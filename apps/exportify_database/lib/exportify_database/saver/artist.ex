defmodule ExportifyDatabase.Saver.Artist do
  alias ExportifyDatabase.Models.Artist, as: ModelArtist
  alias ExportifySpotify.RequestHandler.AccessData
  alias ExportifySpotify.RequestHandler.SpotifyRequest
  alias ExportifySpotify.RequestHandler

  @artist_url "https://api.spotify.com/v1/artists/"

  def save_one(artist) do
    artist
    |> struct_to_model
    |> ExportifyDatabase.Util.Helpers.get_or_insert()
  end

  def save_all(artists) do
    artists
    |> Enum.map(&(struct_to_model(&1)))
    |> ExportifyDatabase.Util.Helpers.get_or_insert_all
  end

  def download_and_save(artists, %AccessData{} = access_data) do
    artists
    |> Enum.map(&(
      %SpotifyRequest{
        url: @artist_url <> &1,
        params: %{}
      }))
    |> Enum.map(&(
      RequestHandler.spotify_request(&1, access_data)
        |> RequestHandler.handle_response
      ))
    |> Enum.map(&(ExportifySpotify.Artist.get_info(&1)))
    |> Enum.map(&(struct_to_model(&1)))
    |>ExportifyDatabase.Util.Helpers.get_or_insert_all
  end

  def struct_to_model(artist) do
    %ModelArtist {
      name: artist.name,
      spotify_id: artist.spotify_id,
      image: artist.image
    }
  end
end