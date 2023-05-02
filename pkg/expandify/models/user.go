package models

import (
	"golang.org/x/oauth2"
	"time"
)

type User struct {
	AccessToken   string    `json:"access_token"`
	TokenType     string    `json:"token_type"`
	RefreshToken  string    `json:"refresh_token"`
	Expiry        time.Time `json:"expiry"`
	ID            string    `json:"id" gorm:"primarykey"`
	SpotifyUserID string
	SpotifyUser   *SpotifyUser `json:"spotify_user"`
}

func NewUser(spotifyUser *SpotifyUser, token *oauth2.Token) User {
	return User{
		AccessToken:   token.AccessToken,
		TokenType:     token.TokenType,
		RefreshToken:  token.RefreshToken,
		Expiry:        token.Expiry,
		ID:            spotifyUser.ID,
		SpotifyUserID: spotifyUser.ID,
		SpotifyUser:   spotifyUser,
	}
}
