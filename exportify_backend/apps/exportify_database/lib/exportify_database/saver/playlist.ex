defmodule ExportifyDatabase.Saver.Playlist do
  alias ExportifyDatabase.Models.Playlist, as: ModelPlaylist
  alias ExportifySpotify.RequestHandler.AccessData
  alias ExportifyDatabase.Util.Helpers, as: DB


  def save_one(playlist, %AccessData{} = access_data) do
    playlist
    |> create_model(access_data)
    |> DB.get_or_insert
  end


  def save_all(playlists, %AccessData{} = access_data) do
    playlists
    |> Enum.map(&(create_model(&1, access_data)))
    |> DB.get_or_insert_all
  end


  def create_model(playlist, %AccessData{} = access_data) do
    songs = ExportifyDatabase.Saver.Song.save_all(playlist.songs, access_data)

    %ModelPlaylist {
      name: playlist.name,
      song_count: playlist.song_count,
      spotify_id: playlist.spotify_id,
      songs: songs
    }
  end
end
