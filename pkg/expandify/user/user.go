package user

import (
	"expandify-api/pkg/expandify"
	"expandify-api/pkg/spotify_client"
)

type User interface {
	SpotifyLoginUrl(state string) string
	CompleteSpotifyLogin(code string, state string) (stateData string, userId string, err error)
}

type user struct {
	spotifyClient  spotify_client.SpotifyClient
	userRepository Repository
	encryptionKey  *[32]byte
}

func New(
	client spotify_client.SpotifyClient,
	userRepository Repository,
	encryptionKey *[32]byte) User {

	return &user{
		spotifyClient:  client,
		userRepository: userRepository,
		encryptionKey:  encryptionKey,
	}
}

type Repository interface {
	Get(id string) (*expandify.User, error)
	Save(user *expandify.User)
}
