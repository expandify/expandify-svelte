package spotify_user

import (
	"errors"
	"expandify-api/pkg/expandify"
	"time"
)

const syncType = "SpotifyUser"

func (u *spotifyUser) Sync(id string) (*expandify.Sync, error) {
	sync, err := u.repository.GetSync(id)
	if err != nil {
		return nil, err
	}
	if sync.Status == expandify.SyncStatusLoading {
		return nil, errors.New("sync already in process")
	}

	u.startSync(id)
	spotifyUser, err := u.spotifyClient.GetUser(id)
	if err != nil {
		u.errorSync(id, "unable to get spotify user", sync)
		return nil, err
	}

	u.repository.Save(spotifyUser)
	u.stopSync(id, sync)
	return sync, nil
}

func (u *spotifyUser) GetSync(id string) (*expandify.Sync, error) {
	sync, err := u.repository.GetSync(id)
	if err != nil {
		return nil, err
	}
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
