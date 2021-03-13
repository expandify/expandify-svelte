defmodule SpotifyCommunicator.Playlist do

  import SpotifyCommunicator.Helpers
  use SpotifyCommunicator.Responder

  alias SpotifyCommunicator.{
    Client,
    TimedObj,
    Paging,
    Playlist
  }

  @derive Jason.Encoder
  defstruct ~w[
    collaborative
    description
    external_urls
    followers
    href
    id
    images
    name
    owner
    public
    snapshot_id
    tracks
    type
    uri
  ]a

  def get_playlist(access_token, playlist_id, params \\ []) do
    url = get_playlist_url(playlist_id, params)
    access_token |> Client.get(url) |> handle_response
  end

  def get_playlist_url(playlist_id, params \\ []) do
    "https://api.spotify.com/v1/playlists/#{playlist_id}" <> query_string(params)
  end


  def get_playlist_tracks(access_token, playlist_id, params \\ []) do
    url = get_playlist_tracks_url(playlist_id, params)
    access_token |> Client.get(url) |> handle_response
  end


  def get_playlist_tracks_url(playlist_id, params \\ []) do
    playlist_tracks_url(playlist_id) <> query_string(params)
  end

  @doc false
  def playlist_tracks_url(playlist_id) do
    get_playlist_url(playlist_id) <> "/tracks"
  end

  @doc """
  Implements the hook expected by the Responder behaviour
  """
  def build_response(body) do
    case body do
      %{"tracks" => _} -> build_playlist(body)
      %{"items" => _} -> TimedObj.build_paged_timed_obj(body, :track, SpotifyCommunicator.Track)
    end
  end

  def build_playlist(playlist) do
    struct = to_struct(Playlist, playlist)
    Map.put(struct, :tracks, TimedObj.build_paged_timed_obj(struct.tracks, :track, SpotifyCommunicator.Track))
  end

  def build_paged_playlists(body) do
    %Paging{
      href: body["href"],
      items: Enum.map(body["items"], &to_struct(Playlist, &1)),
      limit: body["limit"],
      next: body["next"],
      offset: body["offset"],
      previous: body["previous"],
      total: body["total"]
    }
  end

end
