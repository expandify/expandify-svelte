package spotify_client

import (
	"context"
	"expandify-api/pkg/expandify"
	"expandify-api/pkg/expandify/user"
	"github.com/zmb3/spotify/v2"
	spotifyauth "github.com/zmb3/spotify/v2/auth"
	"golang.org/x/oauth2"
)

type SpotifyClient interface {
	GetAuthenticator() *spotifyauth.Authenticator
	GetUser(id string) (*expandify.SpotifyUser, error)
	GetUserFromToken(token *oauth2.Token) (*expandify.SpotifyUser, error)
}

type spotifyClient struct {
	authenticator  *spotifyauth.Authenticator
	userRepository user.Repository
}

func NewSpotifyClient(clientId string, clientSecret string, redirectUri string, userRepository user.Repository) SpotifyClient {
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
		userRepository: userRepository,
	}
}

type refreshingClientWrapper[T any] struct {
	spotifyClient *spotifyClient
}

type refreshingClientFunc[T any] func(client *spotify.Client) (T, error)

func (r *refreshingClientWrapper[T]) withUserID(id string, fun refreshingClientFunc[T]) (T, error) {
	u, err := r.spotifyClient.userRepository.Get(id)
	token := oauth2.Token{}
	if err != nil {
		return r.withUserToken(&token, fun)
	}
	token.AccessToken = u.AccessToken
	token.RefreshToken = u.RefreshToken
	token.TokenType = u.TokenType
	token.Expiry = u.Expiry
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

func (sc *spotifyClient) GetAuthenticator() *spotifyauth.Authenticator {
	return sc.authenticator
}
