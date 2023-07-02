package spotify_user

import "expandify-api/pkg/expandify"

func (u *spotifyUser) GetSync(user *expandify.User) (*expandify.Sync, error) {
	sync, err := u.repository.GetSync(user.ID)
	if err != nil {
		return nil, err
	}
	return sync, nil
}
