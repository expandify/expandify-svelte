defmodule SpotifyCommunicator.User do

  defstruct ~w[
    birthdate
    country
    display_name
    email
    external_urls
    followers
    href
    id
    images
    product
    type
    uri
  ]a

  alias SpotifyCommunicator.{
    Client,
    Track,
    Artist,
    TimedObj,
    Album,
    Playlist,
    User
  }
  use SpotifyCommunicator.Responder
  import SpotifyCommunicator.Helpers

  def me(access_token) do
    access_token |> Client.get(me_url()) |> handle_response
  end

  def me_url, do: "https://api.spotify.com/v1/me"


  def user(access_token, user_id) do
    url = user_url(user_id)
    access_token |> Client.get(url) |> handle_response
  end

  def user_url(user_id), do: "https://api.spotify.com/v1/users/#{user_id}"

  def get_saved_tracks(access_token, params \\ []) do
    url = get_saved_tracks_url(params)

    access_token |> Client.get(url) |> handle_response
  end

  def get_saved_tracks_url(params) do
    "https://api.spotify.com/v1/me/tracks" <> query_string(params)
  end

  def get_followed_artists(access_token, params \\ []) do
    url = get_followed_artists_url(params)
    access_token |> Client.get(url) |> handle_response
  end

  def get_followed_artists_url(params) do
    "https://api.spotify.com/v1/me/following?type=artist&" <> URI.encode_query(params)
  end

  def get_saved_albums(access_token, params \\ []) do
    url = get_saved_albums_url(params)
    access_token |> Client.get(url) |> handle_response
  end

  def get_saved_albums_url(params) do
    "https://api.spotify.com/v1/me/albums" <> query_string(params)
  end

  def get_saved_playlists(access_token, params \\ []) do
    url = get_saved_playlists_url(params)
    access_token |> Client.get(url) |> handle_response
  end

  def get_saved_playlists_url(params) do
    "https://api.spotify.com/v1/me/playlists" <> query_string(params)
  end

  def build_response(body) do
    case body do
      %{"items" => %{"track" => _}} -> TimedObj.build_paged_timed_obj(body, :track, Track)
      %{"artists" => _} -> Artist.build_paged_artists(body)
      %{"items" => %{"album" => _}} -> TimedObj.build_paged_timed_obj(body, :album, Album)
      %{"items" => %{"type" => "playlist"}} -> Playlist.build_paged_playlists(body)
      %{"email" => _ } -> to_struct(User, body)
    end
  end
end
