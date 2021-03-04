defmodule ExportifyWeb.SongsLive do
  @moduledoc """
     Live View module for the songs tab.
  """
  use ExportifyWeb, :live_view

  @impl true
  def mount(_params, session, socket) do

    case ExportifyLocalStorage.Library.get_pid(session["library_pids"], :songs_pid) do
      :error -> redirect(socket, to: ExportifyWeb.Router.Helpers.index_path(socket, :index))
      {:ok, songs_pid} -> ExportifyLocalStorage.LibraryStorage.subscribe(songs_pid, self())
                          {:ok, assign(socket, %{songs: ExportifyLocalStorage.LibraryStorage.get_items(songs_pid), expanded: ""})}
    end
  end

  @impl true
  def handle_info({:update, songs, _sender}, socket) do
    updated =
      socket
      |> update(:songs, fn (_) -> songs end)
    {:noreply, updated}
  end

  @impl true
  def render(assigns) do
    Phoenix.View.render(ExportifyWeb.BackupView, "songs.html", assigns)
  end
end
