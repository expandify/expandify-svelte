package embeds

import "expandify-api/pkg/expandify"

type Followers struct {
	Count    uint   `json:"total"`
	Endpoint string `json:"href"`
}

func NewFollowers(followers *expandify.Followers) Followers {
	if followers == nil {
		return Followers{}
	}

	return Followers{
		Count:    followers.Count,
		Endpoint: followers.Href,
	}
}

func (f Followers) Convert() expandify.Followers {
	return expandify.Followers{
		Count: f.Count,
		Href:  f.Endpoint,
	}
}
