package user

import (
	"context"
	"errors"
	"expandify-api/pkg/expandify"
	spotifyauth "github.com/zmb3/spotify/v2/auth"
)

func (a *user) SpotifyLoginUrl(state string) string {
	encrypted, _ := encrypt(state, a.encryptionKey)
	return a.spotifyClient.GetAuthenticator().AuthURL(encrypted, spotifyauth.ShowDialog)
}

func (a *user) CompleteSpotifyLogin(code string, state string) (stateData string, userId string, err error) {

	decrypted, err := decrypt(state, a.encryptionKey)
	if err != nil {
		return "", "", errors.New("invalid state")
	}

	tok, err := a.spotifyClient.GetAuthenticator().Exchange(context.Background(), code)
	if err != nil {
		return decrypted, "", err
	}

	privateUser, err := a.spotifyClient.GetFromToken(tok).CurrentUser(context.Background())
	if err != nil {
		return decrypted, "", err
	}

	spotifyUser := expandify.NewSpotifyUser(privateUser)
	expandifyUser := expandify.NewUser(spotifyUser, tok.TokenType, tok.AccessToken, tok.RefreshToken, tok.Expiry)

	a.userRepository.Save(expandifyUser)

	return decrypted, expandifyUser.SpotifyUser.SpotifyID, nil
}
