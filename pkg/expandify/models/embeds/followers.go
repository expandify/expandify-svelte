package embeds

import "github.com/zmb3/spotify/v2"

type Followers struct {
	Count    uint   `json:"total"`
	Endpoint string `json:"href"`
}

func NewFollowers(followers *spotify.Followers) Followers {
	if followers == nil {
		return Followers{}
	}

	return Followers{
		Count:    followers.Count,
		Endpoint: followers.Endpoint,
	}
}
