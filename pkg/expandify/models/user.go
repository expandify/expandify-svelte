package models

import (
	"golang.org/x/oauth2"
	"gorm.io/gorm"
	"time"
)

type User struct {
	gorm.Model
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
		Model:         gorm.Model{},
		AccessToken:   token.AccessToken,
		TokenType:     token.TokenType,
		RefreshToken:  token.RefreshToken,
		Expiry:        token.Expiry,
		SpotifyUserID: spotifyUser.ID,
		SpotifyUser:   spotifyUser,
	}
}
