defmodule ExportifyLocalStorage.Songs do
  @moduledoc """
      Responsible for retrieving and saving songs.
  """
  alias ExportifySpotify.RequestHandler.AccessData

  @user_songs_url "https://api.spotify.com/v1/me/tracks"
  @song_limit 50

  @doc """
    Starts the storage process for the songs.
    Starts a task to retrieve all songs for the user.
    The user is identified by the given access token.
  """
  def start_download(token) do
    expires_in = token.expires_in
    {:ok, songs_storage} = ExportifyLocalStorage.LibraryStorage.start_link(expires_in)
    access_data = %AccessData{access_token: token.access_token, storage_pid: songs_storage}
    Task.start_link(fn -> ExportifyLocalStorage.Item.backup_from_link(@user_songs_url, access_data, &(process_item/2), %{"limit" => @song_limit}) end)
    songs_storage
  end

  @doc """
    Processes a response.
    Updates the storage process with all the songs in the response.
    Returns the processed items and the url for the next request.
  """
  def process_item(response, %AccessData{} = user) do
    processed_items =
      response
      |> Map.fetch!("items")
      |> Enum.map(&(ExportifySpotify.Song.get_info/1))
      |> Enum.map(&(ExportifyLocalStorage.LibraryStorage.update_item(user.storage_pid, &1)))

    {:ok, processed_items, Map.fetch!(response, "next")}
  end



  def start_import(library_id, expires_in) do

    {:ok, songs_storage} = ExportifyLocalStorage.LibraryStorage.start(expires_in)
    songs_storage
  end
end
