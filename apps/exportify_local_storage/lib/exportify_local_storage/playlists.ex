defmodule ExportifyLocalStorage.Playlists do
  @moduledoc """
      Responsible for retrieving and saving playlists.
  """
  alias ExportifySpotify.RequestHandler.AccessData

  @playlists_request_url "https://api.spotify.com/v1/me/playlists"

  @doc """
    Starts the storage process for the playlists.
    Starts a task to retrieve all playlists for the user.
    The user is identified by the given access token.
  """
  def start_download(token) do
    expires_in = token.expires_in
    {:ok, playlists_storage} = ExportifyLocalStorage.LibraryStorage.start_link(expires_in)
    access_data = %AccessData{access_token: token.access_token, storage_pid: playlists_storage}
    Task.start_link(fn -> ExportifyLocalStorage.Item.backup_from_link(@playlists_request_url, access_data, &(process_item/2)) end)
    playlists_storage
  end

  @doc """
    Processes a response.
    Updates the storage process with all the playlists in the response.
    Returns the processed items and the url for the next request.
  """
  def process_item(response, %AccessData{} = user) do
    processed_items =
      response
      |> Map.fetch!("items")
      |> Enum.map(&(get_playlist_info(&1, user)))
      |> Enum.map(&(ExportifyLocalStorage.LibraryStorage.update_item(user.storage_pid, &1)))

    {:ok, processed_items, Map.fetch!(response, "next")}
  end

  def get_playlist_info(spotify_playlist_info, user) do
    songs = ExportifyLocalStorage.Item.backup_from_link(spotify_playlist_info["tracks"]["href"], user, &(process_playlist_songs(&1, &2)))
    ExportifySpotify.Playlist.get_info(spotify_playlist_info, songs)
  end

  @doc false
  defp process_playlist_songs(response, _user) do
    processed_items =
      response
      |> Map.fetch!("items")
      |> Enum.map(&(ExportifySpotify.Song.get_info(&1)))
    {:ok, processed_items, Map.fetch!(response, "next")}
  end

  def start_import(library_id, expires_in) do

    {:ok, playlists_storage} = ExportifyLocalStorage.LibraryStorage.start(expires_in)
    playlists_storage
  end
end
