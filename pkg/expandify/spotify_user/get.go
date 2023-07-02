package spotify_user

import (
	"errors"
	"expandify-api/pkg/expandify"
	"fmt"
)

func (u *spotifyUser) Get(user *expandify.User) (*expandify.SpotifyUser, error) {
	sync, err := u.GetSync(user)
	if err != nil {
		return nil, err
	}
	switch sync.Status {
	case expandify.SyncStatusError:
		return nil, errors.New(fmt.Sprintf("sync error: '%s'", sync.Error.Error()))
	case expandify.SyncStatusNever:
		return nil, errors.New("not synced yet")
	case expandify.SyncStatusLoading:
		return nil, errors.New("not finished syncing yet")
	}

	spotifyUser, err := u.repository.Get(user.ID)
	if err != nil {
		return nil, err
	}
	return spotifyUser, nil
}
