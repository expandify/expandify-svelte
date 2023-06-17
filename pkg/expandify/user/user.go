package user

import (
	"expandify-api/pkg/expandify"
)

type User interface {
	SpotifyLoginUrl(state string) string
	CompleteSpotifyLogin(code string, state string) (stateData string, userId string, err error)
}

type user struct {
	spotifyClient  expandify.SpotifyClient
	userRepository Repository
	encryptionKey  *[32]byte
}

func New(
	client expandify.SpotifyClient,
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
