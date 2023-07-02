package spotify_user

import (
	"expandify-api/pkg/expandify"
)

type SpotifyUser interface {
	Get(user *expandify.User) (*expandify.SpotifyUser, error)
	Sync(user *expandify.User) (*expandify.Sync, error)
	GetSync(user *expandify.User) (*expandify.Sync, error)
}

type spotifyUser struct {
	spotifyClient expandify.SpotifyClient
	repository    Repository
}

func New(client expandify.SpotifyClient, repository Repository) SpotifyUser {
	return &spotifyUser{
		spotifyClient: client,
		repository:    repository,
	}
}

type Repository interface {
	Get(id string) (*expandify.SpotifyUser, error)
	Save(user *expandify.SpotifyUser)
	GetSync(id string) (*expandify.Sync, error)
	SaveSync(id string, sync *expandify.Sync)
}
