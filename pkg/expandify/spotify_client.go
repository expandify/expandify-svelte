package expandify

import (
	"context"
	"github.com/zmb3/spotify/v2"
	spotifyauth "github.com/zmb3/spotify/v2/auth"
	"golang.org/x/oauth2"
)

type SpotifyClient interface {
	GetAuthenticator() *spotifyauth.Authenticator
	Get(user *User) *spotify.Client
	GetFromToken(token *oauth2.Token) *spotify.Client
}

type spotifyClient struct {
	authenticator *spotifyauth.Authenticator
}

func NewSpotifyClient(clientId string, clientSecret string, redirectUri string) SpotifyClient {
	return &spotifyClient{
		authenticator: spotifyauth.New(
			spotifyauth.WithScopes(
				spotifyauth.ScopePlaylistReadPrivate,
				spotifyauth.ScopeUserReadPrivate,
				spotifyauth.ScopePlaylistReadCollaborative,
				spotifyauth.ScopeUserFollowRead,
				spotifyauth.ScopeUserLibraryRead,
				spotifyauth.ScopeUserReadEmail,
				spotifyauth.ScopeUserReadCurrentlyPlaying,
				spotifyauth.ScopeUserReadPlaybackState,
				spotifyauth.ScopeUserReadRecentlyPlayed,
				spotifyauth.ScopeUserTopRead,
			),
			spotifyauth.WithClientID(clientId),
			spotifyauth.WithClientSecret(clientSecret),
			spotifyauth.WithRedirectURL(redirectUri),
		),
	}
}

func (sc *spotifyClient) GetAuthenticator() *spotifyauth.Authenticator {
	return sc.authenticator
}

func (sc *spotifyClient) Get(user *User) *spotify.Client {
	return sc.GetFromToken(user.SpotifyToken)
}

func (sc *spotifyClient) GetFromToken(token *oauth2.Token) *spotify.Client {
	httpClient := sc.GetAuthenticator().Client(context.Background(), token)
	return spotify.New(httpClient, spotify.WithRetry(true))
}
