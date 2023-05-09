package expandify

import (
	"context"
	"github.com/zmb3/spotify/v2"
	spotifyauth "github.com/zmb3/spotify/v2/auth"
	"golang.org/x/oauth2"
)

type SpotifyClient interface {
	GetAuthenticator() *spotifyauth.Authenticator
	GetUser(id string) (*SpotifyUser, error)
	GetUserFromToken(token *oauth2.Token) (*SpotifyUser, error)
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

type refreshingClientWrapper[T any] struct {
	spotifyClient *spotifyClient
}

type refreshingClientFunc[T any] func(client *spotify.Client) (T, error)

func (r *refreshingClientWrapper[T]) withUserID(id string, fun refreshingClientFunc[T]) (T, error) {
	user, ok := r.spotifyClient.repository.GetUser(id)
	token := oauth2.Token{}
	if ok {
		token.AccessToken = user.AccessToken
		token.RefreshToken = user.RefreshToken
		token.TokenType = user.TokenType
		token.Expiry = user.Expiry
	}
	return r.withUserToken(&token, fun)
}

func (r *refreshingClientWrapper[T]) withUserToken(token *oauth2.Token, fun refreshingClientFunc[T]) (T, error) {
	httpClient := r.spotifyClient.GetAuthenticator().Client(context.Background(), token)
	client := spotify.New(httpClient, spotify.WithRetry(true))
	t, err := fun(client)
	if err != nil {
		return t, err
	}

	// TODO Check if token was refreshed and save the new one

	return t, nil
}

func (sc *spotifyClient) getUserFunc() refreshingClientFunc[*spotify.PrivateUser] {
	return func(client *spotify.Client) (*spotify.PrivateUser, error) {
		return client.CurrentUser(context.Background())
	}
}

func (sc *spotifyClient) GetAuthenticator() *spotifyauth.Authenticator {
	return sc.authenticator
}

func (sc *spotifyClient) GetUserFromToken(token *oauth2.Token) (*SpotifyUser, error) {
	wrapper := refreshingClientWrapper[*spotify.PrivateUser]{spotifyClient: sc}

	user, err := wrapper.withUserToken(token, sc.getUserFunc())
	if err != nil {
		return nil, err
	}
	return NewSpotifyUser(user), nil
}

func (sc *spotifyClient) GetUser(id string) (*SpotifyUser, error) {
	wrapper := refreshingClientWrapper[*spotify.PrivateUser]{spotifyClient: sc}

	user, err := wrapper.withUserID(id, sc.getUserFunc())
	if err != nil {
		return nil, err
	}
	return NewSpotifyUser(user), nil
}
