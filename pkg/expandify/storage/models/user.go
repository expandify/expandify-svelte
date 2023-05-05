package models

import (
	"expandify-api/pkg/expandify"
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

func NewUser(user *expandify.User) *User {
	return &User{
		AccessToken:   user.AccessToken,
		TokenType:     user.TokenType,
		RefreshToken:  user.RefreshToken,
		Expiry:        user.Expiry,
		ID:            user.SpotifyUser.SpotifyID,
		SpotifyUserID: user.SpotifyUser.SpotifyID,
		SpotifyUser:   NewSpotifyUser(user.SpotifyUser),
	}
}

func (u User) Convert() *expandify.User {
	return &expandify.User{
		AccessToken:  u.AccessToken,
		TokenType:    u.TokenType,
		RefreshToken: u.RefreshToken,
		Expiry:       u.Expiry,
		SpotifyUser:  (*u.SpotifyUser).Convert(),
		Syncs:        []expandify.Sync{},
	}
}
