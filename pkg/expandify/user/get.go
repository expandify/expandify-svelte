package user

import (
	"errors"
	"expandify-api/pkg/expandify"
	"fmt"
)

func (u *user) Get(id string) (*expandify.SpotifyUser, error) {
	sync, err := u.GetSync(id)
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

	user, err := u.repository.Get(id)
	if err != nil {
		return nil, err
	}
	return user, nil
}
