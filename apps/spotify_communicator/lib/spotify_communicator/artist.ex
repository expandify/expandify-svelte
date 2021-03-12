defmodule SpotifyCommunicator.Artist do

  import SpotifyCommunicator.Helpers
  use SpotifyCommunicator.Responder

  alias SpotifyCommunicator.{
    Artist,
    Client,
    Paging,
    Track,
    Album
  }

  @derive Jason.Encoder
  defstruct ~w[
    external_urls
    followers
    genres
    href
    id
    images
    name
    popularity
    type
    uri
  ]a


  @doc """
    Get Artist by Id.
  """
  def get_artist(access_token, id) do
    url = get_artist_url(id)
    access_token |> Client.get(url) |> handle_response
  end

  def get_artist_url(id) do
    "https://api.spotify.com/v1/artists/#{id}"
  end

  @doc """
    Get multiple artists by Id.
  """
  def get_artists(access_token, params = [ids: _ids]) do
    url = get_artists_url(params)
    access_token |> Client.get(url) |> handle_response
  end


  def get_artists_url(params) do
    "https://api.spotify.com/v1/artists" <> query_string(params)
  end

  @doc """
    Get all albums from an artist.
  """
  def get_artists_albums(access_token, id) do
    url = get_artists_albums_url(id)
    access_token |> Client.get(url) |> handle_response
  end

  def get_artists_albums_url(id) do
    "https://api.spotify.com/v1/artists/#{id}/albums"
  end

  @doc """
    Get related artists to a given artist id.
  """
  def get_related_artists(access_token, id) do
    url = get_related_artists_url(id)
    access_token |> Client.get(url) |> handle_response
  end


  def get_related_artists_url(id) do
    "https://api.spotify.com/v1/artists/#{id}/related-artists"
  end

  @doc """
    Get an artists top tracks.
  """
  def get_top_tracks(access_token, id, params) do
    url = get_top_tracks_url(id, params)
    access_token |> Client.get(url) |> handle_response
  end


  def get_top_tracks_url(id, params) do
    "https://api.spotify.com/v1/artists/#{id}/top-tracks" <> query_string(params)
  end

  def build_response(body) do
    case body do
      %{"name" => _} -> to_struct(Artist, body) # response type for: get one artist
      %{"artists" => artists} -> build_artists(artists) # response type for: get multiple artists and get related artists
      %{"items" => _} -> Album.build_paged_albums(body) # response type for: get an artists album
      %{"tracks" => tracks} -> Enum.map(tracks, &to_struct(Track, &1)) # response tye for: get artists top tracks
    end
  end

  @doc false
  def build_paged_artists(%{"artists" => response}) do
    %Paging{
      items: build_artists(response["items"]),
      next: response["next"],
      total: response["total"],
      cursors: response["cursors"],
      limit: response["limit"],
      href: response["href"]
    }
  end

  @doc false
  def build_artists(artists) do
    Enum.map(artists, &to_struct(Artist, &1))
  end
end
