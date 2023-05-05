package user

import (
	"errors"
	"expandify-api/pkg/expandify"
)

func (u *user) Get(id string) (*expandify.SpotifyUser, error) {
	user, success := u.repository.GetSpotifyUser(id)
	if !success {
		return nil, errors.New("no user found")
	}
	return user, nil
}
