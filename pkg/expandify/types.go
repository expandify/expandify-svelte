package expandify

import (
	"time"
)

type Album struct {
	AlbumType            string            `json:"album_type"`
	AvailableMarkets     []string          `json:"available_markets"`
	ExternalURLs         map[string]string `json:"external_urls"`
	Href                 string            `json:"href"`
	SpotifyID            string            `json:"id"`
	Images               []Image           `json:"images"`
	Name                 string            `json:"name"`
	ReleaseDate          string            `json:"release_date"`
	ReleaseDatePrecision string            `json:"release_date_precision"`
	URI                  string            `json:"uri"`
	Copyrights           []Copyright       `json:"copyrights"`
	ExternalIDs          ExternalIDs       `json:"external_ids"`
	Genres               []string          `json:"genres"`
	Popularity           int               `json:"popularity"`
	Artists              []*Artist         `json:"artists"`
	Tracks               []*Track          `json:"tracks"`
	// Not available in github.com/zmb3/spotify/v2 yet
	//Restrictions       string            `json:"restrictions"`
	//Type               string            `json:"type"`
	//Type               string            `json:"type"`
	//Label              string            `json:"label"`
}

type Artist struct {
	ExternalURLs map[string]string `json:"external_urls"`
	Followers    Followers         `json:"followers"`
	Genres       []string          `json:"genres"`
	Href         string            `json:"href"`
	SpotifyID    string            `json:"spotify_id"`
	Images       []Image           `json:"images"`
	Name         string            `json:"name"`
	Popularity   int               `json:"popularity"`
	URI          string            `json:"uri"`
	// Not available in github.com/zmb3/spotify/v2 yet
	//Type  	 string            `json:"type"`
}

type Copyright struct {
	Text string `json:"text"`
	Type string `json:"type"`
}

type ExternalIDs struct {
	ISRC string `json:"isrc"`
	EAN  string `json:"ean"`
	UPC  string `json:"upc"`
}

type Followers struct {
	Count uint   `json:"total"`
	Href  string `json:"href"`
}

type Image struct {
	Height int    `json:"height"`
	Width  int    `json:"width"`
	URL    string `json:"url"`
}

type Library struct {
	Creation  time.Time   `json:"creation"`
	Owner     *User       `json:"owner"`
	Albums    []*Album    `json:"albums"`
	Artists   []*Artist   `json:"artists"`
	Playlists []*Playlist `json:"playlists"`
	Tracks    []*Track    `json:"tracks"`
}

type SyncStatus int64

const (
	SyncStatusNever SyncStatus = iota
	SyncStatusLoading
	SyncStatusSynced
	SyncStatusError
)

type Sync struct {
	Type    string     `json:"type"`
	Status  SyncStatus `json:"status"`
	Error   error      `json:"error"`
	Current int        `json:"current"`
	Max     int        `json:"max"`
	Start   time.Time  `json:"start"`
	End     time.Time  `json:"end"`
}

type Playlist struct {
	Collaborative bool              `json:"collaborative"`
	Description   string            `json:"description"`
	ExternalURLs  map[string]string `json:"external_urls"`
	Followers     Followers         `json:"followers"`
	Href          string            `json:"href"`
	SpotifyID     string            `json:"spotify_id"`
	Images        []Image           `json:"images"`
	Name          string            `json:"name"`
	Owner         SpotifyUserPublic `json:"owner"`
	IsPublic      bool              `json:"public"`
	SnapshotID    string            `json:"snapshot_id"`
	Tracks        []*PlaylistTrack  `json:"tracks"`
	URI           string            `json:"uri"`
	// Not available in github.com/zmb3/spotify/v2 yet
	//Type 	     string            	`json:"type"`
}

type PlaylistTrack struct {
	AddedAt string            `json:"added_at"`
	AddedBy SpotifyUserPublic `json:"added_by"`
	IsLocal bool              `json:"is_local"`
	Track   Track             `json:"track"`
}

type SpotifyUser struct {
	DisplayName  string            `json:"display_name"`
	ExternalURLs map[string]string `json:"external_urls"`
	Followers    Followers         `json:"followers"`
	Href         string            `json:"href"`
	SpotifyID    string            `json:"spotify_id"`
	Images       []Image           `json:"images"`
	URI          string            `json:"uri"`
	Country      string            `json:"country"`
	Email        string            `json:"email"`
	Product      string            `json:"product"`
	Birthdate    string            `json:"birthdate"`
}

type SpotifyUserPublic struct {
	DisplayName  string            `json:"display_name"`
	ExternalURLs map[string]string `json:"external_urls"`
	Followers    Followers         `json:"followers"`
	Href         string            `json:"href"`
	SpotifyID    string            `json:"spotify_id"`
	Images       []Image           `json:"images"`
	URI          string            `json:"uri"`
}

type Track struct {
	Album   TrackAlbum `json:"album"`
	Artists []struct {
		Name         string            `json:"name"`
		SpotifyID    string            `json:"spotify_id"`
		URI          string            `json:"uri"`
		Href         string            `json:"href"`
		ExternalURLs map[string]string `json:"external_urls"`
	} `json:"artists"`
	AvailableMarkets []string          `json:"available_markets"`
	DiscNumber       int               `json:"disc_number"`
	Duration         int               `json:"duration_ms"`
	Explicit         bool              `json:"explicit"`
	ExternalURLs     map[string]string `json:"external_urls"`
	ExternalIDs      ExternalIDs       `json:"external_ids"`
	Href             string            `json:"href"`
	SpotifyID        string            `json:"spotify_id"`
	IsPlayable       *bool             `json:"is_playable"`
	Name             string            `json:"name"`
	Popularity       int               `json:"popularity"`
	PreviewURL       string            `json:"preview_url"`
	TrackNumber      int               `json:"track_number"`
	URI              string            `json:"uri"`
	Type             string            `json:"type"`
	// Not available in github.com/zmb3/spotify/v2 yet
	//Restrictions 	string  			`json:"restrictions"`
	//IsLocal      	boolean 			`json:"is_local"`
}

type TrackAlbum struct {
	Artists []struct {
		Name      string `json:"name"`
		SpotifyID string `json:"spotify_id"`
	} `json:"artists"`
	Name                 string            `json:"name"`
	AlbumType            string            `json:"album_type"`
	SpotifyID            string            `json:"spotify_id"`
	URI                  string            `json:"uri"`
	AvailableMarkets     []string          `json:"available_markets"`
	Href                 string            `json:"href"`
	Images               []Image           `json:"images"`
	ExternalURLs         map[string]string `json:"external_urls"`
	ReleaseDate          string            `json:"release_date"`
	ReleaseDatePrecision string            `json:"release_date_precision"`
}

type User struct {
	AccessToken  string       `json:"access_token"`
	TokenType    string       `json:"token_type"`
	RefreshToken string       `json:"refresh_token"`
	Expiry       time.Time    `json:"expiry"`
	SpotifyUser  *SpotifyUser `json:"spotify_user"`
	UserSync     *Sync        `json:"user_sync"`
	AlbumSync    *Sync        `json:"album_sync"`
	ArtistSync   *Sync        `json:"artist_sync"`
	PlaylistSync *Sync        `json:"playlist_sync"`
	TrackSync    *Sync        `json:"track_sync"`
}
