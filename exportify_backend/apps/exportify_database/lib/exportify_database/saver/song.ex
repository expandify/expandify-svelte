defmodule ExportifyDatabase.Saver.Song do
  alias ExportifyDatabase.Models.Song, as: ModelSong
  alias ExportifySpotify.RequestHandler.AccessData
  alias ExportifyDatabase.Util.Helpers, as: DB


  def save_one(song, %AccessData{} = access_data) do
    song
    |> create_model(access_data)
    |> DB.get_or_insert
  end


  def save_all(songs, %AccessData{} = access_data) do
    songs
    |> Enum.map(&(create_model(&1, access_data)))
    |> DB.get_or_insert_all
  end


  def create_model(song, %AccessData{} = access_data) do
    artists =
      song.artists
      |> Enum.map(&(&1.spotify_id))
      |> ExportifyDatabase.Saver.Artist.download_and_save(access_data)

    album =
      ExportifyDatabase.Saver.Album.download_and_save([song.album.spotify_id], access_data)
      |> Enum.at(0)

    %ModelSong {
      name: song.name,
      duration: song.duration,
      artists: artists,
      album: album,
      spotify_id: song.spotify_id
    }
  end
end
