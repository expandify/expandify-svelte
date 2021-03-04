defmodule ExportifyWeb.ArtistsLive do
  @moduledoc """
     Live View module for the artists tab.
  """
  use ExportifyWeb, :live_view

  @impl true
  def mount(_params, session, socket) do

    case ExportifyLocalStorage.Library.get_pid(session["library_pids"], :artists_pid) do
      :error -> redirect(socket, to: ExportifyWeb.Router.Helpers.index_path(socket, :index))
      {:ok, artists_pid} -> ExportifyLocalStorage.LibraryStorage.subscribe(artists_pid, self())
                           {:ok, assign(socket, %{artists: ExportifyLocalStorage.LibraryStorage.get_items(artists_pid)})}
    end
  end

  @impl true
  def handle_info({:update, artists, _sender}, socket) do

    updated =
      socket
      |> update(:artists, fn(_) -> artists end)
    {:noreply, updated}
  end

  @impl true
  def render(assigns) do
    Phoenix.View.render(ExportifyWeb.BackupView, "artists.html", assigns)
  end
end
