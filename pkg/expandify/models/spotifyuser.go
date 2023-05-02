package models

import (
	"expandify-api/pkg/expandify/models/embeds"
	"github.com/zmb3/spotify/v2"
	"gorm.io/datatypes"
	"gorm.io/gorm"
)

type SpotifyUser struct {
	gorm.Model
	DisplayName  string                                `json:"display_name"`
	ExternalURLs datatypes.JSONType[map[string]string] `json:"external_urls"`
	Followers    embeds.Followers                      `json:"followers" gorm:"embedded;embeddedPrefix:followers_"`
	Endpoint     string                                `json:"href"`
	ID           string                                `json:"id" gorm:"primarykey"`
	Images       datatypes.JSONType[[]embeds.Image]    `json:"images"`
	URI          string                                `json:"uri"`
	Country      string                                `json:"country"`
	Email        string                                `json:"email"`
	Product      string                                `json:"product"`
	Birthdate    string                                `json:"birthdate"`
}

func NewSpotifyUser(user *spotify.PrivateUser) SpotifyUser {
	var images []embeds.Image
	for _, image := range user.Images {
		images = append(images, embeds.NewImage(&image))
	}

	return SpotifyUser{
		Model:        gorm.Model{},
		DisplayName:  user.DisplayName,
		ExternalURLs: datatypes.NewJSONType(user.ExternalURLs),
		Followers:    embeds.NewFollowers(&user.Followers),
		Endpoint:     user.Endpoint,
		ID:           user.ID,
		Images:       datatypes.NewJSONType(images),
		URI:          string(user.URI),
		Country:      user.Country,
		Email:        user.Email,
		Product:      user.Product,
		Birthdate:    user.Birthdate,
	}
}
