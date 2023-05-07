package models

import (
	"expandify-api/pkg/expandify"
	"expandify-api/pkg/expandify/storage/models/embeds"
	"gorm.io/datatypes"
	"time"
)

type User struct {
	AccessToken   string                          `json:"access_token"`
	TokenType     string                          `json:"token_type"`
	RefreshToken  string                          `json:"refresh_token"`
	Expiry        time.Time                       `json:"expiry"`
	ID            string                          `json:"id" gorm:"primarykey"`
	SpotifyUser   *SpotifyUser                    `json:"spotify_user"`
	UserSync      datatypes.JSONType[embeds.Sync] `json:"user_sync" gorm:"foreignKey:UserSyncID"`
	AlbumSync     datatypes.JSONType[embeds.Sync] `json:"album_sync"  gorm:"foreignKey:AlbumSyncID"`
	ArtistSync    datatypes.JSONType[embeds.Sync] `json:"artist_sync"  gorm:"foreignKey:ArtistSyncID"`
	PlaylistSync  datatypes.JSONType[embeds.Sync] `json:"playlist_sync" gorm:"foreignKey:PlaylistSyncID"`
	TrackSync     datatypes.JSONType[embeds.Sync] `json:"track_sync"  gorm:"foreignKey:TrackSyncID"`
	SpotifyUserID string
}

func NewUser(user *expandify.User) *User {
	return &User{
		AccessToken:   user.AccessToken,
		TokenType:     user.TokenType,
		RefreshToken:  user.RefreshToken,
		Expiry:        user.Expiry,
		ID:            user.SpotifyUser.SpotifyID,
		SpotifyUser:   NewSpotifyUser(user.SpotifyUser),
		UserSync:      datatypes.NewJSONType(*embeds.NewSync(user.UserSync)),
		AlbumSync:     datatypes.NewJSONType(*embeds.NewSync(user.AlbumSync)),
		ArtistSync:    datatypes.NewJSONType(*embeds.NewSync(user.ArtistSync)),
		PlaylistSync:  datatypes.NewJSONType(*embeds.NewSync(user.PlaylistSync)),
		TrackSync:     datatypes.NewJSONType(*embeds.NewSync(user.TrackSync)),
		SpotifyUserID: user.SpotifyUser.SpotifyID,
	}
}

func (u User) Convert() *expandify.User {
	return &expandify.User{
		AccessToken:  u.AccessToken,
		TokenType:    u.TokenType,
		RefreshToken: u.RefreshToken,
		Expiry:       u.Expiry,
		SpotifyUser:  u.SpotifyUser.Convert(),
		UserSync:     u.UserSync.Data().Convert(),
		AlbumSync:    u.AlbumSync.Data().Convert(),
		ArtistSync:   u.ArtistSync.Data().Convert(),
		PlaylistSync: u.PlaylistSync.Data().Convert(),
		TrackSync:    u.TrackSync.Data().Convert(),
	}
}
