package expandify

import (
	"context"
	"github.com/zmb3/spotify/v2"
	spotifyauth "github.com/zmb3/spotify/v2/auth"
	"golang.org/x/oauth2"
)

type SpotifyClient interface {
	GetAuthenticator() *spotifyauth.Authenticator
	FromToken(token *oauth2.Token) *spotify.Client
	FromUserID(id string) *spotify.Client
}

type spotifyClient struct {
	authenticator *spotifyauth.Authenticator
	repository    Repository
}

func NewSpotifyClient(clientId string, clientSecret string, redirectUri string, repository Repository) SpotifyClient {
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
		repository: repository,
	}
}

func (sc *spotifyClient) FromUserID(id string) *spotify.Client {
	user, ok := sc.repository.GetUser(id)
	token := oauth2.Token{}
	if ok {
		token.AccessToken = user.AccessToken
		token.RefreshToken = user.RefreshToken
		token.TokenType = user.TokenType
		token.Expiry = user.Expiry
	}
	return sc.FromToken(&token)
}

func (sc *spotifyClient) FromToken(token *oauth2.Token) *spotify.Client {
	httpClient := sc.GetAuthenticator().Client(context.Background(), token)
	return spotify.New(httpClient, spotify.WithRetry(true))
}

func (sc *spotifyClient) GetAuthenticator() *spotifyauth.Authenticator {
	return sc.authenticator
}
