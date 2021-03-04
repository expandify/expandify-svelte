defmodule ExportifyDatabase.Saver.Album do
  alias ExportifyDatabase.Models.Album, as: ModelAlbum
  alias ExportifySpotify.RequestHandler.AccessData
  alias ExportifySpotify.RequestHandler.SpotifyRequest
  alias ExportifyDatabase.Util.Helpers, as: DB

  @album_url "https://api.spotify.com/v1/albums/"

  def save_one(album, %AccessData{} = access_data) do
    album
    |> create_model(access_data)
    |> DB.get_or_insert
  end


  def save_all(albums, %AccessData{} = access_data) do
    albums
    |> Enum.map(&(create_model(&1, access_data)))
    |> DB.get_or_insert_all
  end


  def create_model(album, %AccessData{} = access_data) do
    artists =
      album.artists
      |> Enum.map(&(&1.spotify_id))
      |> ExportifyDatabase.Saver.Artist.download_and_save(access_data)

    %ModelAlbum {
      name: album.name,
      spotify_id: album.spotify_id,
      image: album.image,
      artists: artists
    }
  end


  def download_and_save(albums, %AccessData{} = access_data) do
    albums
    |> Enum.map(&(%SpotifyRequest{url: @album_url <> &1, params: %{}}))
    |> Enum.map(&(ExportifySpotify.RequestHandler.spotify_request(&1, access_data)
                  |> ExportifySpotify.RequestHandler.handle_response))
    |> Enum.map(&(ExportifySpotify.Album.get_info(&1)))
    |> Enum.map(&(create_model(&1, access_data)))
    |> DB.get_or_insert_all
  end

end
