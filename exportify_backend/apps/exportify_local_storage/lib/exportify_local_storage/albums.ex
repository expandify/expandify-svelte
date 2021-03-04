defmodule ExportifyLocalStorage.Albums do
  @moduledoc """
      Responsible for retrieving and saving albums.
  """
  alias ExportifySpotify.RequestHandler.AccessData

  @album_follow_link "https://api.spotify.com/v1/me/albums"

  @doc """
    Starts the storage process for the albums.
    Starts a task to retrieve all albums for the user.
    The user is identified by the given access token.
  """
  def start_download(token) do
    expires_in = token.expires_in
    {:ok, album_storage} = ExportifyLocalStorage.LibraryStorage.start_link(expires_in)
    access_data = %AccessData{access_token: token.access_token, storage_pid: album_storage}
    Task.start_link(fn -> ExportifyLocalStorage.Item.backup_from_link(@album_follow_link, access_data, &(process_item/2)) end)
    album_storage
  end

  @doc """
    Processes a response.
    Updates the storage process with all the albums in the response.
    Returns the processed items and the url for the next request.
  """
  def process_item(response, %AccessData{} = user) do
    processed_items =
      response
      |> Map.fetch!("items")
      |> Enum.map(&(&1["album"]))
      |> Enum.map(&(ExportifySpotify.Album.get_info(&1)))
      |> Enum.map(&(ExportifyLocalStorage.LibraryStorage.update_item(user.storage_pid, &1)))

    {:ok, processed_items, Map.fetch!(response, "next")}
  end

  def start_import(library_id, expires_in) do

    {:ok, album_storage} = ExportifyLocalStorage.LibraryStorage.start(expires_in)
    Task.start(fn -> get_from_database(album_storage, library_id) end)
    album_storage
  end

  def get_from_database(album_storage, library_id) do
    library = ExportifyDatabase.Repo.get_by(ExportifyDatabase.Models.Library, id: library_id)
    albums =
      ExportifyDatabase.Repo.all(Ecto.assoc(library, :albums))
      |> Enum.map(&(to_map(&1)))
      |> Enum.map(&(ExportifySpotify.Album.get_info/1))
    ExportifyLocalStorage.LibraryStorage.update_items(album_storage, albums)
  end

  def to_map(album) do
    Map.from_struct(album)
    |> Map.put(:artists, get_artists(album))
  end

  def get_artists(album) do
    ExportifyDatabase.Repo.all(Ecto.assoc(album, :artists))
    |> Enum.map(&(Map.take(&1, [:name, :image, :spotify_id])))
  end
end
