defmodule ExportifyWeb.PlaylistsLive do
  @moduledoc """
     Live View module for the playlist tab.
  """
  use ExportifyWeb, :live_view

  @impl true
  def mount(_params, session, socket) do

    case ExportifyLocalStorage.Library.get_pid(session["library_pids"], :playlists_pid) do
      :error -> redirect(socket, to: ExportifyWeb.Router.Helpers.index_path(socket, :index))
      {:ok, playlists_pid} -> ExportifyLocalStorage.LibraryStorage.subscribe(playlists_pid, self())
                         {:ok, assign(socket, %{playlists: ExportifyLocalStorage.LibraryStorage.get_items(playlists_pid), expanded: ""})}
    end
  end

  @impl true
  def handle_info({:update, playlists, _sender}, socket) do
    updated =
      socket
      |> update(:playlists, fn (_) -> playlists end)
    {:noreply, updated}
  end

  @impl true
  def handle_event("expand", %{"playlist_id" => playlist_id}, socket) do
    {
      :noreply,
      update(socket, :expanded, &(if &1 == playlist_id, do: "", else: playlist_id))
    }
  end

  @impl true
  def render(assigns) do
    Phoenix.View.render(ExportifyWeb.BackupView, "playlists.html", assigns)
  end
end
