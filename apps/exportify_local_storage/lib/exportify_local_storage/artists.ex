defmodule ExportifyLocalStorage.Artists do
  @moduledoc """
      Responsible for retrieving and saving artists.
  """
  alias ExportifySpotify.RequestHandler.AccessData

  @artists_follow_url "https://api.spotify.com/v1/me/following?type=artist"

  @doc """
    Starts the storage process for the artists.
    Starts a task to retrieve all artists for the user.
    The user is identified by the given access token.
  """
  def start_download(token) do
    expires_in =  token.expires_in
    {:ok, artist_storage} = ExportifyLocalStorage.LibraryStorage.start_link(expires_in)
    access_data = %AccessData{access_token: token.access_token, storage_pid: artist_storage}
    Task.start_link(fn -> ExportifyLocalStorage.Item.backup_from_link(@artists_follow_url, access_data, &(process_item/2)) end)
    artist_storage
  end

  @doc """
    Processes a response.
    Updates the storage process with all the artists in the response.
    Returns the processed items and the url for the next request.
  """
  def process_item(response, %AccessData{} = user) do
    processed_items =
      response
      |> Map.fetch!("artists")
      |> Map.fetch!("items")
      |> Enum.map(&(ExportifySpotify.Artist.get_info(&1)))
      |> Enum.map(&(ExportifyLocalStorage.LibraryStorage.update_item(user.storage_pid, &1)))

    {:ok, processed_items, response["artists"]["next"]}
  end

  def start_import(library_id, expires_in) do

    {:ok, artists_storage} = ExportifyLocalStorage.LibraryStorage.start(expires_in)
    #Task.start(fn -> get_from_database(artists_storage, library_id) end)
    artists_storage
  end


  defp get_from_database(artists_storage, library_id) do
    library = ExportifyDatabase.Repo.get_by(ExportifyDatabase.Models.Library, id: library_id)
    artists =
      ExportifyDatabase.Repo.all(Ecto.assoc(library, :artists))
      |> Enum.map(&(Map.from_struct(&1)))
      |> Enum.map(&(ExportifySpotify.Artist.get_info/1))
    ExportifyLocalStorage.LibraryStorage.update_items(artists_storage, artists)
  end
end
