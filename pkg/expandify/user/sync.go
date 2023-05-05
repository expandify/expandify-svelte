package user

import (
	"context"
	"expandify-api/pkg/expandify"
	"log"
)

func (u *user) Sync(id string) (*expandify.Sync, error) {
	user, err := u.spotifyClient.FromUserID(id).CurrentUser(context.Background())
	if err != nil {
		return nil, err
	}

	spotifyuser := expandify.NewSpotifyUser(user)
	log.Println(spotifyuser)
	panic("implement me")
}
