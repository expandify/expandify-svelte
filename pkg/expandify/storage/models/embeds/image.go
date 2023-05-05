package embeds

import (
	"expandify-api/pkg/expandify"
)

type Image struct {
	// gorm.Model
	Height int    `json:"height"`
	Width  int    `json:"width"`
	URL    string `json:"url"`
}

func NewImage(image *expandify.Image) Image {
	if image == nil {
		return Image{}
	}

	return Image{
		Height: image.Height,
		Width:  image.Width,
		URL:    image.URL,
	}
}

func (i Image) Convert() expandify.Image {
	return expandify.Image{
		Height: i.Height,
		Width:  i.Width,
		URL:    i.URL,
	}
}
