package embeds

import "github.com/zmb3/spotify/v2"

type Image struct {
	// gorm.Model
	Height int    `json:"height"`
	Width  int    `json:"width"`
	URL    string `json:"url"`
}

func NewImage(image *spotify.Image) Image {
	if image == nil {
		return Image{}
	}

	return Image{
		Height: image.Height,
		Width:  image.Width,
		URL:    image.URL,
	}
}
