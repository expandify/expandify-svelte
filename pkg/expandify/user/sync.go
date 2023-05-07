package user

import (
	"context"
	"errors"
	"expandify-api/pkg/expandify"
	"time"
)

const syncType = "User"

func (u *user) Sync(id string) (*expandify.Sync, error) {
	sync, err := u.repository.GetSync(id)
	if err != nil {
		return nil, err
	}
	if sync.Status == expandify.SyncStatusLoading {
		return nil, errors.New("already in sync process")
	}

	u.startSync(id)
	user, err := u.spotifyClient.FromUserID(id).CurrentUser(context.Background())
	if err != nil {
		u.errorSync(id, "unable to get user", sync)
		return nil, err
	}

	spotifyUser := expandify.NewSpotifyUser(user)
	u.repository.Save(spotifyUser)
	u.stopSync(id, sync)
	return sync, nil
}

func (u *user) GetSync(id string) (*expandify.Sync, error) {
	sync, err := u.repository.GetSync(id)
	if err != nil {
		return nil, err
	}
	return sync, nil
}

func (u *user) startSync(id string) {
	sync := &expandify.Sync{
		Type:    syncType,
		Status:  expandify.SyncStatusLoading,
		Error:   nil,
		Current: 0,
		Max:     1,
		Start:   time.Now(),
		End:     time.Time{},
	}
	u.repository.SaveSync(id, sync)
}

func (u *user) errorSync(id string, error string, sync *expandify.Sync) {
	s := &expandify.Sync{
		Type:    syncType,
		Status:  expandify.SyncStatusError,
		Error:   errors.New(error),
		Current: sync.Current,
		Max:     sync.Max,
		Start:   sync.Start,
		End:     time.Time{},
	}
	u.repository.SaveSync(id, s)
}

func (u *user) stopSync(id string, sync *expandify.Sync) {
	s := &expandify.Sync{
		Type:    syncType,
		Status:  expandify.SyncStatusSynced,
		Error:   nil,
		Current: sync.Max,
		Max:     sync.Max,
		Start:   sync.Start,
		End:     time.Now(),
	}
	u.repository.SaveSync(id, s)
}
