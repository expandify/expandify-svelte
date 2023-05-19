package spotify_client

import (
	"context"
	"expandify-api/pkg/expandify"
	"github.com/zmb3/spotify/v2"
	"golang.org/x/oauth2"
)

func (sc *spotifyClient) getUserFunc() refreshingClientFunc[*spotify.PrivateUser] {
	return func(client *spotify.Client) (*spotify.PrivateUser, error) {
		return client.CurrentUser(context.Background())
	}
}

func (sc *spotifyClient) GetUserFromToken(token *oauth2.Token) (*expandify.SpotifyUser, error) {
	wrapper := refreshingClientWrapper[*spotify.PrivateUser]{spotifyClient: sc}

	u, err := wrapper.withUserToken(token, sc.getUserFunc())
	if err != nil {
		return nil, err
	}
	return expandify.NewSpotifyUser(u), nil
}

func (sc *spotifyClient) GetUser(id string) (*expandify.SpotifyUser, error) {
	wrapper := refreshingClientWrapper[*spotify.PrivateUser]{spotifyClient: sc}

	u, err := wrapper.withUserID(id, sc.getUserFunc())
	if err != nil {
		return nil, err
	}
	return expandify.NewSpotifyUser(u), nil
}
