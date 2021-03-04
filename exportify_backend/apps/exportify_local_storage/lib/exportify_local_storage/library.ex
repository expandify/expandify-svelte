defmodule ExportifyLocalStorage.Library do
  @moduledoc """
      Capsules all library storage into one.
  """
  alias ExportifyLocalStorage.Playlists
  alias ExportifyLocalStorage.Songs
  alias ExportifyLocalStorage.Artists
  alias ExportifyLocalStorage.Albums
  alias ExportifyLocalStorage.User

  @doc """
    Starts the storage process.
  """
  def start_download(token) do
    expires_in =  token.expires_in
    {:ok, library_storage} = ExportifyLocalStorage.LibraryStorage.start_link(expires_in)
    library = %{
      playlists_pid: Playlists.start_download(token),
      songs_pid: Songs.start_download(token),
      artists_pid: Artists.start_download(token),
      albums_pid: Albums.start_download(token),
      user_pid: User.start_download(token)
    }
    ExportifyLocalStorage.LibraryStorage.update_item(library_storage, library)
    library_storage
  end

  def get_pid(library_pids, item_name) do
    libraries = ExportifyLocalStorage.LibraryStorage.get_items(library_pids)
    case libraries do
      [] -> :error
      [library|_] -> Map.fetch(library, item_name)
    end
  end

  def get_pids(library_pids, item_name) do
    libraries = ExportifyLocalStorage.LibraryStorage.get_items(library_pids)
    case libraries do
      [] -> :error
      [_a, _b, _c | _x] -> :error
      _x -> Enum.map(libraries, &(Map.fetch(&1, item_name)))
    end
  end

  def get_all_items(list_of_item_pids) do
    Enum.map(list_of_item_pids, &(get_item(&1)))
  end

  defp get_item({:ok, pid}) do
    ExportifyLocalStorage.LibraryStorage.get_items(pid)
  end
  defp get_item(_), do: nil

  def start_import(library_pids, library_id) do

    expires_in =
      case ExportifyLocalStorage.Library.get_pid(library_pids, :user_pid) do
        :error -> 3600 # This is a temporary default value
        {:ok, user} -> Map.get(Enum.at(ExportifyLocalStorage.LibraryStorage.get_items(user), 0, %{}), :expires_in, 3600)
      end

    library = %{
      albums_pid: Albums.start_import(library_id, expires_in),
      artists_pid: Artists.start_import(library_id, expires_in),
      playlists_pid: Playlists.start_import(library_id, expires_in),
      songs_pid: Songs.start_import(library_id, expires_in)
    }

    ExportifyLocalStorage.LibraryStorage.update_item(library_pids, library)
    library_pids
  end
end
