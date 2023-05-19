package models

import (
	"expandify-api/pkg/expandify"
	embeds2 "expandify-api/pkg/storage/models/embeds"
	"gorm.io/datatypes"
)

type SpotifyUser struct {
	DisplayName  string
	ExternalURLs datatypes.JSONType[map[string]string] `json:"external_urls"`
	Followers    embeds2.Followers                     `json:"followers" gorm:"embedded;embeddedPrefix:followers_"`
	Href         string                                `json:"href"`
	ID           string                                `json:"id" gorm:"primarykey"`
	Images       datatypes.JSONType[[]embeds2.Image]   `json:"images"`
	URI          string                                `json:"uri"`
	Country      string                                `json:"country"`
	Email        string                                `json:"email"`
	Product      string                                `json:"product"`
	Birthdate    string                                `json:"birthdate"`
}

func NewSpotifyUser(spotifyUser *expandify.SpotifyUser) *SpotifyUser {
	var images []embeds2.Image
	for _, image := range spotifyUser.Images {
		images = append(images, embeds2.NewImage(&image))
	}

	return &SpotifyUser{
		DisplayName:  spotifyUser.DisplayName,
		ExternalURLs: datatypes.NewJSONType(spotifyUser.ExternalURLs),
		Followers:    embeds2.NewFollowers(&spotifyUser.Followers),
		Href:         spotifyUser.Href,
		ID:           spotifyUser.SpotifyID,
		Images:       datatypes.NewJSONType(images),
		URI:          spotifyUser.URI,
		Country:      spotifyUser.Country,
		Email:        spotifyUser.Email,
		Product:      spotifyUser.Product,
		Birthdate:    spotifyUser.Birthdate,
	}
}

func (s *SpotifyUser) Convert() *expandify.SpotifyUser {
	var images []expandify.Image
	for _, image := range s.Images.Data() {
		images = append(images, image.Convert())
	}

	return &expandify.SpotifyUser{
		DisplayName:  s.DisplayName,
		ExternalURLs: s.ExternalURLs.Data(),
		Followers:    s.Followers.Convert(),
		Href:         s.Href,
		SpotifyID:    s.ID,
		Images:       images,
		URI:          s.URI,
		Country:      s.Country,
		Email:        s.Email,
		Product:      s.Product,
		Birthdate:    s.Birthdate,
	}
}
