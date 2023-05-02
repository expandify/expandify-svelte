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
	clientId     string
	clientSecret string
	redirectUri  string
}

func NewSpotifyClient(clientId string, clientSecret string, redirectUri string) SpotifyClient {
	return &spotifyClient{
		clientId:     clientId,
		clientSecret: clientSecret,
		redirectUri:  redirectUri,
	}
}

// GetClient TODO maybe with userId?
func (sc *spotifyClient) GetClient(token *oauth2.Token) *spotify.Client {
	httpClient := sc.GetAuthenticator().Client(context.Background(), token)
	return spotify.New(httpClient)
}

func (sc *spotifyClient) GetAuthenticator() *spotifyauth.Authenticator {
	return spotifyauth.New(
		spotifyauth.WithScopes(
			spotifyauth.ScopeUserReadPrivate,
			spotifyauth.ScopePlaylistReadPrivate,
			spotifyauth.ScopePlaylistReadCollaborative,
			spotifyauth.ScopeUserFollowRead,
			spotifyauth.ScopeUserLibraryRead,
			spotifyauth.ScopeUserReadPrivate,
			spotifyauth.ScopeUserReadEmail,
			spotifyauth.ScopeUserReadCurrentlyPlaying,
			spotifyauth.ScopeUserReadPlaybackState,
			spotifyauth.ScopeUserReadRecentlyPlayed,
			spotifyauth.ScopeUserTopRead,
		),
		spotifyauth.WithClientID(sc.clientId),
		spotifyauth.WithClientSecret(sc.clientSecret),
		spotifyauth.WithRedirectURL(sc.redirectUri),
	)
}
