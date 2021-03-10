defmodule SpotifyCommunicator.Track do

  alias SpotifyCommunicator.{
    Client,
    Track,
    Paging
  }

  import SpotifyCommunicator.Helpers
  use SpotifyCommunicator.Responder

  @derive Jason.Encoder
  defstruct ~w[
    album
    artists
    available_markets
    disc_number
    duration_ms
    explicit
    external_ids
    href
    id
    is_playable
    linked_from
    name
    popularity
    preview_url
    track_number
    type
    uri
  ]a

  @doc """
    Get the current users saved tracks.
  """
  def get_saved_tracks(conn, params \\ []) do
    url = saved_tracks_url(params)

    conn
    |> Client.get(url)
    |> handle_response
  end

  def saved_tracks_url(params) do
    "https://api.spotify.com/v1/me/tracks" <> query_string(params)
  end

  @doc """
    Get audio features of multiple tracks.
  """
  def audio_features(access_token, params) when is_list(params) do
    url = audio_features_url(params)
    access_token |> Client.get(url) |> handle_response
  end

  @doc """
    Get audio features of one track
  """
  def audio_features(access_token, id) do
    url = audio_features_url(id)
    access_token |> Client.get(url) |> handle_response
  end

  def audio_features_url(params) when is_list(params) do
    "https://api.spotify.com/v1/audio-features" <> query_string(params)
  end

  def audio_features_url(id) do
    "https://api.spotify.com/v1/audio-features/#{id}"
  end

  @doc """
    Get multiple tracks by ids.
  """
  def get_tracks(access_token, params) do
    url = get_tracks_url(params)
    access_token |> Client.get(url) |> handle_response
  end

  @doc """
    Get one track by id.
  """
  def get_track(access_token, id) do
    url = get_track_url(id)
    access_token |> Client.get(url) |> handle_response
  end

  def get_tracks_url(params) do
    "https://api.spotify.com/v1/tracks" <> query_string(params)
  end

  def get_track_url(id) do
    "https://api.spotify.com/v1/tracks/#{id}"
  end

  def build_response(body) do
    case body do
      %{"audio_features" => audio_features} -> build_audio_features(audio_features) # response type for: get audio features for multiple tracks
      %{"tracks" => tracks} -> build_tracks(tracks) # response type for: get multiple tracks
      %{"album" => _} -> to_struct(Track, body) # response type for: get one track
      %{"energy" => _} -> to_struct(Spotify.AudioFeatures, body) # response type for: get audio features for one track

      %{"items" => _} = response -> build_paged_response(response) # response type for: get saved tracks
    end
  end

  @doc false
  def build_tracks(tracks) do
    Enum.map(tracks, &to_struct(Track, &1))
  end

  @doc false
  defp build_paged_response(response) do
    %Paging{
      href: response["href"],
      items: response["items"] |>Enum.map(fn %{"track" => items} -> items end) |> build_tracks(),
      limit: response["limit"],
      next: response["next"],
      offset: response["offset"],
      previous: response["previous"],
      total: response["total"]
    }
  end

  @doc false
  def build_audio_features(audio_features) do
    Enum.map(audio_features, &to_struct(Spotify.AudioFeatures, &1))
  end
end
