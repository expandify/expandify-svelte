package expandify

type Repository interface {
	GetUser(id string) (*User, bool)
	SaveUser(user *User)
	GetSpotifyUser(id string) (*SpotifyUser, bool)
	SaveSpotifyUser(spotifyUser *SpotifyUser)
}
