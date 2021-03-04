defmodule ExportifyWeb.AlbumsLive do
  @moduledoc """
     Live View module for the albums tab.
  """
  use ExportifyWeb, :live_view

  @impl true
  def mount(_params, session, socket) do

    case ExportifyLocalStorage.Library.get_pids(session["library_pids"], :albums_pid) do
      :error -> redirect(socket, to: ExportifyWeb.Router.Helpers.index_path(socket, :index))
      albums_pids -> subscribe_to_all(albums_pids)
                    {:ok, assign(socket, %{list_of_albums: ExportifyLocalStorage.Library.get_all_items(albums_pids)})}
    end
  end

  defp subscribe_to_all(albums_pids) do
    Enum.map(albums_pids, &(subscribe_to_one/1))
  end

  defp subscribe_to_one({:ok, pid}) do
     ExportifyLocalStorage.LibraryStorage.subscribe(pid, self())
  end
  defp subscribe_to_one(_), do: nil

  @impl true
  def handle_info({:update, _, _}, socket) do

    library_pids = socket.assigns["library_pids"]
    albums_pids = ExportifyLocalStorage.Library.get_pids(library_pids, :albums_pid)

    updated =
      socket
      |> update(:list_of_albums, fn (_) -> ExportifyLocalStorage.Library.get_all_items(albums_pids) end)
    {:noreply, updated}
  end


  @impl true
  def render(assigns) do
    Phoenix.View.render(ExportifyWeb.BackupView, "albums.html", assigns)
  end
end
