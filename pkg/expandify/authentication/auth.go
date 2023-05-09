package authentication

import (
	"context"
	"errors"
	"expandify-api/pkg/expandify"
	spotifyauth "github.com/zmb3/spotify/v2/auth"
)

type Auth interface {
	Url(state string) string
	CompleteAuth(code string, state string) (stateData string, userId string, err error)
}

type auth struct {
	spotifyClient expandify.SpotifyClient
	repository    expandify.Repository
	encryptionKey *[32]byte
}

func New(client expandify.SpotifyClient, repository expandify.Repository, encryptionKey *[32]byte) Auth {
	return &auth{
		spotifyClient: client,
		repository:    repository,
		encryptionKey: encryptionKey,
	}
}

func (a *auth) Url(state string) string {
	encrypted, _ := encrypt(state, a.encryptionKey)
	return a.spotifyClient.GetAuthenticator().AuthURL(encrypted, spotifyauth.ShowDialog)
}

func (a *auth) CompleteAuth(code string, state string) (stateData string, userId string, err error) {

	decrypted, err := decrypt(state, a.encryptionKey)
	if err != nil {
		return "", "", errors.New("invalid state")
	}

	tok, err := a.spotifyClient.GetAuthenticator().Exchange(context.Background(), code)
	if err != nil {
		return decrypted, "", err
	}

	spotifyUser, err := a.spotifyClient.GetUserFromToken(tok)
	if err != nil {
		return decrypted, "", err
	}

	user := expandify.NewUser(spotifyUser, tok.TokenType, tok.AccessToken, tok.RefreshToken, tok.Expiry)

	a.repository.SaveUser(user)
	a.repository.SaveSpotifyUser(spotifyUser)

	return decrypted, user.SpotifyUser.SpotifyID, nil
}
