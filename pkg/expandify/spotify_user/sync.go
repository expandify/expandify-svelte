package spotify_user

import (
	"context"
	"errors"
	"expandify-api/pkg/expandify"
	"time"
)

const syncType = "SpotifyUser"

func (u *spotifyUser) Sync(user *expandify.User) (*expandify.Sync, error) {
	userId := user.SpotifyUser.SpotifyID
	sync, err := u.repository.GetSync(userId)
	if err != nil {
		return nil, err
	}
	if sync.Status == expandify.SyncStatusLoading {
		return nil, errors.New("sync already in process")
	}

	u.startSync(userId)

	privateUser, err := u.spotifyClient.Get(user).CurrentUser(context.Background())
	if err != nil {
		u.errorSync(userId, "unable to get spotify user", sync)
		return nil, err
	}

	spotifyUser := expandify.NewSpotifyUser(privateUser)
	u.repository.Save(spotifyUser)
	u.stopSync(userId, sync)
	return sync, nil
}

func (u *spotifyUser) startSync(id string) {
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

func (u *spotifyUser) errorSync(id string, error string, sync *expandify.Sync) {
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

func (u *spotifyUser) stopSync(id string, sync *expandify.Sync) {
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
