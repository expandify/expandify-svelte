defmodule ExportifyWeb.LibraryLive do
  @moduledoc """
     Live View module for the library tab.
  """
  use ExportifyWeb, :live_view
  alias ExportifyWeb.{PlaylistsLive, SongsLive, ArtistsLive, AlbumsLive, LibrariesLive}
  alias ExportifyLocalStorage.Library

  @impl true
  def mount(_params, session, socket) do

    if not ExportifyWeb.LoginController.session_valid?(session) do
      {:ok, redirect(socket, to: ExportifyWeb.Router.Helpers.index_path(socket, :index))}
    else
      {:ok, assign(socket, Map.merge(%{current_content: nil}, session))}
    end
  end

  @impl true
  def handle_event("test-flash", _, socket) do
    new_socket =
      socket
      |> put_flash(:info, "FLASH INFO TEST!!")
      |> put_flash(:error, "FLASH ERROR TEST!!")
    {:noreply, new_socket}
  end

  @impl true
  def handle_event("download-library", _, socket) do
    {:noreply, redirect(socket, external: ExportifyWeb.Router.Helpers.download_url(socket, :download))}
  end

  @impl true
  def handle_event("save-library", _, socket) do

    albums = ExportifyLocalStorage.LibraryStorage.get_items(pid_from_assign(socket, :albums_pid))
    artists = ExportifyLocalStorage.LibraryStorage.get_items(pid_from_assign(socket, :artists_pid))
    playlists = ExportifyLocalStorage.LibraryStorage.get_items(pid_from_assign(socket, :playlists_pid))
    songs = ExportifyLocalStorage.LibraryStorage.get_items(pid_from_assign(socket, :songs_pid))
    user = Enum.at(ExportifyLocalStorage.LibraryStorage.get_items(pid_from_assign(socket, :user_pid)), 0)
    ExportifyDatabase.Saver.Library.save_library(user, artists, albums, songs, playlists)

    {:noreply, socket}
  end

  defp pid_from_assign(socket, pid_id) do
    {:ok, pid} = Library.get_pid(socket.assigns["library_pids"], pid_id)
    pid
  end

  @impl true
  def handle_params(_params, _uri, socket) do
    socket =
      case socket.assigns.live_action do
        :playlists -> update(socket, :current_content, fn (_) -> PlaylistsLive end)
        :songs -> update(socket, :current_content, fn (_) -> SongsLive end)
        :artists -> update(socket, :current_content, fn (_) -> ArtistsLive end)
        :albums -> update(socket, :current_content, fn (_) -> AlbumsLive end)
        :libraries -> update(socket, :current_content, fn (_) -> LibrariesLive end)
        _ -> socket
      end
    {:noreply, socket}
  end

  @impl true
  def render(assigns) do
    Phoenix.View.render(ExportifyWeb.BackupView, "library.html", assigns)
  end

end
