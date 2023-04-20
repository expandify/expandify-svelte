defmodule Api.SpotifyService do
  
  alias Api.Spotify.{
    Artists,
    Albums,
    Playlists,
    Tracks,
    User
  }

  def get(current_user, :user), do: User.get_current_user(current_user);
  def get(current_user, :artists), do: Artists.get_artists(current_user)
  def get(current_user, :albums), do: Albums.get_albums(current_user)
  def get(current_user, :playlists), do: Playlists.get_playlists(current_user)
  def get(current_user, :tracks), do: Tracks.get_tracks(current_user)

  def get(current_user, :artist, id), do: Artists.get_artist(current_user, id)
  def get(current_user, :album, id), do: Albums.get_album(current_user, id)
  def get(current_user, :playlist, id), do: Playlists.get_playlist(current_user, id)
  def get(current_user, :track, id), do: Tracks.get_track(current_user, id)


end
