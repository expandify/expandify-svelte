package user

import (
	"expandify-api/pkg/expandify"
)

type User interface {
	Sync(id string) (*expandify.Sync, error)
	Get(id string) (*expandify.SpotifyUser, error)
}

type user struct {
	spotifyClient expandify.SpotifyClient
	repository    expandify.Repository
}

func New(client expandify.SpotifyClient, repository expandify.Repository) User {
	return &user{
		spotifyClient: client,
		repository:    repository,
	}
}
