package spotify_user

import (
	"expandify-api/pkg/expandify"
	"expandify-api/pkg/spotify_client"
)

type SpotifyUser interface {
	Get(id string) (*expandify.SpotifyUser, error)
	Sync(id string) (*expandify.Sync, error)
	GetSync(id string) (*expandify.Sync, error)
}

type spotifyUser struct {
	spotifyClient spotify_client.SpotifyClient
	repository    Repository
}

func New(client spotify_client.SpotifyClient, repository Repository) SpotifyUser {
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
