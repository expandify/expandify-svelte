package expandify

import (
	"context"
	"github.com/zmb3/spotify/v2"
	spotifyauth "github.com/zmb3/spotify/v2/auth"
	"golang.org/x/oauth2"
)

type SpotifyClient interface {
	GetAuthenticator() *spotifyauth.Authenticator
	GetClient(token *oauth2.Token) *spotify.Client
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

// GetClient TODO maybe with userId?
func (sc *spotifyClient) GetClient(token *oauth2.Token) *spotify.Client {
	httpClient := sc.GetAuthenticator().Client(context.Background(), token)
	return spotify.New(httpClient)
}

func (sc *spotifyClient) GetAuthenticator() *spotifyauth.Authenticator {
	return sc.authenticator
}
