package expandify

import (
	"github.com/zmb3/spotify/v2"
	"time"
)

func NewAlbum(album *spotify.FullAlbum, artists []*Artist, tracks []*Track) *Album {
	var images []Image
	for _, image := range album.Images {
		images = append(images, *NewImage(&image))
	}
	var copyrights []Copyright
	for _, copyright := range album.Copyrights {
		copyrights = append(copyrights, *NewCopyright(&copyright))
	}

	return &Album{
		AlbumType:            album.AlbumType,
		AvailableMarkets:     album.AvailableMarkets,
		ExternalURLs:         album.ExternalIDs,
		Href:                 album.Endpoint,
		SpotifyID:            string(album.ID),
		Images:               images,
		Name:                 album.Name,
		ReleaseDate:          album.ReleaseDate,
		ReleaseDatePrecision: album.ReleaseDatePrecision,
		URI:                  string(album.URI),
		Copyrights:           copyrights,
		ExternalIDs:          *NewExternalIds(&album.ExternalIDs),
		Genres:               album.Genres,
		Popularity:           album.Popularity,
		Artists:              artists,
		Tracks:               tracks,
	}
}

func NewArtist(artist *spotify.FullArtist) *Artist {
	var images []Image
	for _, image := range artist.Images {
		images = append(images, *NewImage(&image))
	}

	return &Artist{
		ExternalURLs: artist.ExternalURLs,
		Followers:    *NewFollowers(&artist.Followers),
		Genres:       artist.Genres,
		Href:         artist.Endpoint,
		SpotifyID:    string(artist.ID),
		Images:       images,
		Name:         artist.Name,
		Popularity:   artist.Popularity,
		URI:          string(artist.URI),
	}
}

func NewCopyright(copyright *spotify.Copyright) *Copyright {
	return &Copyright{
		Text: copyright.Text,
		Type: copyright.Type,
	}
}

func NewExternalIds(externalIDs *map[string]string) *ExternalIDs {
	return &ExternalIDs{
		ISRC: (*externalIDs)["ISRC"],
		EAN:  (*externalIDs)["EAN"],
		UPC:  (*externalIDs)["UPC"],
	}
}

func NewFollowers(followers *spotify.Followers) *Followers {
	return &Followers{
		Count: followers.Count,
		Href:  followers.Endpoint,
	}
}

func NewImage(image *spotify.Image) *Image {
	return &Image{
		Height: image.Height,
		Width:  image.Width,
		URL:    image.URL,
	}
}

func NewLibrary() *Library {
	return &Library{
		Creation:  time.Time{},
		Owner:     nil,
		Albums:    nil,
		Artists:   nil,
		Playlists: nil,
		Tracks:    nil,
	}
}

func NewSync() *Sync {
	return &Sync{
		Type:      "",
		Status:    0,
		Error:     nil,
		Current:   0,
		Max:       0,
		Start:     time.Time{},
		End:       time.Time{},
		Estimated: time.Time{},
	}
}

func NewPlaylist(playlist *spotify.FullPlaylist, playlistTracks []*PlaylistTrack) *Playlist {
	var images []Image
	for _, image := range playlist.Images {
		images = append(images, *NewImage(&image))
	}

	return &Playlist{
		Collaborative: playlist.Collaborative,
		Description:   playlist.Description,
		ExternalURLs:  playlist.ExternalURLs,
		Followers:     *NewFollowers(&playlist.Followers),
		Href:          playlist.Endpoint,
		SpotifyID:     string(playlist.ID),
		Images:        images,
		Name:          playlist.Name,
		Owner:         *NewSpotifyUserPublic(&playlist.Owner),
		IsPublic:      playlist.IsPublic,
		SnapshotID:    playlist.SnapshotID,
		Tracks:        playlistTracks,
		URI:           string(playlist.URI),
	}
}

func NewPlaylistTrack(playlistTrack *spotify.PlaylistTrack) *PlaylistTrack {
	return &PlaylistTrack{
		AddedAt: playlistTrack.AddedAt,
		AddedBy: *NewSpotifyUserPublic(&playlistTrack.AddedBy),
		IsLocal: playlistTrack.IsLocal,
		Track:   *NewTrack(&playlistTrack.Track),
	}
}

func NewSpotifyUser(user *spotify.PrivateUser) *SpotifyUser {
	var images []Image
	for _, image := range user.Images {
		images = append(images, *NewImage(&image))
	}

	return &SpotifyUser{
		DisplayName:  user.DisplayName,
		ExternalURLs: user.ExternalURLs,
		Followers:    *NewFollowers(&user.Followers),
		Href:         user.Endpoint,
		SpotifyID:    user.ID,
		Images:       images,
		URI:          string(user.URI),
		Country:      user.Country,
		Email:        user.Email,
		Product:      user.Product,
		Birthdate:    user.Birthdate,
	}
}

func NewSpotifyUserPublic(user *spotify.User) *SpotifyUserPublic {
	var images []Image
	for _, image := range user.Images {
		images = append(images, *NewImage(&image))
	}

	return &SpotifyUserPublic{
		DisplayName:  user.DisplayName,
		ExternalURLs: user.ExternalURLs,
		Followers:    *NewFollowers(&user.Followers),
		Href:         user.Endpoint,
		SpotifyID:    user.ID,
		Images:       images,
		URI:          string(user.URI),
	}
}

func NewTrack(track *spotify.FullTrack) *Track {
	return &Track{
		Album:            TrackAlbum{},
		Artists:          nil,
		AvailableMarkets: nil,
		DiscNumber:       0,
		Duration:         0,
		Explicit:         false,
		ExternalURLs:     nil,
		ExternalIDs:      ExternalIDs{},
		Href:             "",
		SpotifyID:        "",
		IsPlayable:       nil,
		Name:             "",
		Popularity:       0,
		PreviewURL:       "",
		TrackNumber:      0,
		URI:              "",
		Type:             "",
	}
}

func NewTrackAlbum(album *spotify.SimpleAlbum) *TrackAlbum {
	var images []Image
	for _, image := range album.Images {
		images = append(images, *NewImage(&image))
	}

	var artists []struct{ Name, SpotifyID string }
	for _, artist := range album.Artists {
		artists = append(artists, struct{ Name, SpotifyID string }{artist.Name, string(artist.ID)})
	}

	return &TrackAlbum{
		Artists: []struct {
			Name      string `json:"name"`
			SpotifyID string `json:"spotify_id"`
		}(artists),
		Name:                 album.Name,
		AlbumType:            album.AlbumType,
		SpotifyID:            string(album.ID),
		URI:                  string(album.URI),
		AvailableMarkets:     album.AvailableMarkets,
		Href:                 album.Endpoint,
		Images:               images,
		ExternalURLs:         album.ExternalURLs,
		ReleaseDate:          album.ReleaseDate,
		ReleaseDatePrecision: album.ReleaseDatePrecision,
	}
}

func NewUser(
	spotifyUser *SpotifyUser,
	TokenType string,
	AccessToken string,
	RefreshToken string,
	Expiry time.Time) *User {

	return &User{
		AccessToken:  AccessToken,
		TokenType:    TokenType,
		RefreshToken: RefreshToken,
		Expiry:       Expiry,
		SpotifyUser:  spotifyUser,
		Syncs:        []Sync{},
	}
}
