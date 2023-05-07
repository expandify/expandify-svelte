package user

import (
	"expandify-api/pkg/expandify"
)

type User interface {
	Get(id string) (*expandify.SpotifyUser, error)
	Sync(id string) (*expandify.Sync, error)
	GetSync(id string) (*expandify.Sync, error)
}

type user struct {
	spotifyClient expandify.SpotifyClient
	repository    Repository
}

func New(client expandify.SpotifyClient, repository Repository) User {
	return &user{
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
