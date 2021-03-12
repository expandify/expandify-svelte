defmodule SpotifyCommunicator.Album do

  use SpotifyCommunicator.Responder
  import SpotifyCommunicator.Helpers

  alias SpotifyCommunicator.{
    Album,
    Client,
    Paging,
    Track
  }

  @derive Jason.Encoder
  defstruct ~w[
    album_type
    artists
    available_markets
    copyrights
    external_ids
    external_urls
    genres
    href
    id
    images
    name
    popularity
    release_date
    release_date_precision
    tracks
    type
    label
  ]a

  @doc """
    Get an album by id.
  """
  def get_album(access_token, id, params \\ []) do
    url = get_album_url(id, params)
    access_token |> Client.get(url) |> handle_response
  end


  def get_album_url(id, params \\ []) do
    "https://api.spotify.com/v1/albums/#{id}" <> query_string(params)
  end

  @doc """
    Get multiple albums by id.
  """
  def get_albums(access_token, params) do
    url = get_albums_url(params)
    access_token |> Client.get(url) |> handle_response
  end


  def get_albums_url(params) do
    "https://api.spotify.com/v1/albums" <> query_string(params)
  end

  @doc """
    Get the tracks of an album.
  """
  def get_album_tracks(access_token, id, params \\ []) do
    url = get_album_tracks_url(id, params)
    access_token |> Client.get(url) |> handle_response
  end


  def get_album_tracks_url(id, params \\ []) do
    "https://api.spotify.com/v1/albums/#{id}/tracks" <> query_string(params)
  end

  @doc """
  Implement the callback required by the Responder behavior
  """
  def build_response(body) do
    case body do
      %{"albums" => albums} -> build_albums(albums) # response type for get multiple albums
      %{"items" => _} -> Track.build_paged_tracks(body) # response type for get_album_tracks
      %{"album_type" => _} -> build_album(body) # response type for get a single album
    end
  end

  @doc false
  def build_album(album) do
    album = to_struct(Album, album)
    tracks = Track.build_paged_tracks(album.tracks)
    Map.put(album, :tracks, tracks)
  end

  @doc false
  def build_albums(albums), do: Enum.map(albums, &build_album/1)

  @doc false
  def build_paged_albums(response) do
    %Paging{
      href: response["href"],
      items: response["items"] |> Track.build_paged_tracks,
      limit: response["limit"],
      next: response["next"],
      offset: response["offset"],
      previous: response["previous"],
      total: response["total"]
    }
  end
end
