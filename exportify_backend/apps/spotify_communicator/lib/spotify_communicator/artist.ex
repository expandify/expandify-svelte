defmodule SpotifyCommunicator.Artist do

  import SpotifyCommunicator.Helpers
  use SpotifyCommunicator.Responder

  alias SpotifyCommunicator.{
    Artist,
    Client,
    Paging,
    Track
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
    Get an artists top tracks.
  """
  def get_top_tracks(access_token, id, params) do
    url = get_top_tracks_url(id, params)
    access_token |> Client.get(url) |> handle_response
  end


  def get_top_tracks_url(id, params) do
    "https://api.spotify.com/v1/artists/#{id}/top-tracks" <> query_string(params)
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
    Get artists I follow.
  """
  def artists_I_follow(access_token, params \\ []) do
    url = artists_I_follow_url(params)
    access_token |> Client.get(url) |> handle_response
  end


  def artists_I_follow_url(params) do
    "https://api.spotify.com/v1/me/following?type=artist&" <> URI.encode_query(params)
  end


  def build_response(body) do
    case body do
      %{"artists" => %{"items" => _items}} = response -> build_paged_artists(response) # response type for: get followed artists
      %{"artists" => artists} -> build_artists(artists) # response type for: get multiple artists
      %{"tracks" => tracks} -> Track.build_tracks(tracks) # response tye for: get artists top tracks
      %{"name" => _} -> to_struct(Artist, body) # response type for: get one artist
      booleans_or_error -> booleans_or_error
    end
  end

  @doc false
  defp build_paged_artists(%{"artists" => response}) do
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
