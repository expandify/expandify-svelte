defmodule ExportifyWeb.LibrariesLive do
  @moduledoc """
     Live View module for the libraries tab.
  """
  use ExportifyWeb, :live_view
  alias ExportifyDatabase.Repo
  import Ecto.Query, only: [from: 2]

  @impl true
  def mount(_params, session, socket) do

    case ExportifyLocalStorage.Library.get_pid(session["library_pids"], :user_pid) do
      :error -> redirect(socket, to: ExportifyWeb.Router.Helpers.index_path(socket, :index))
      {:ok, user_pid} -> ExportifyLocalStorage.LibraryStorage.subscribe(user_pid, self())
                            {:ok, assign(socket, %{library_pids: session["library_pids"], libraries: get_libraries(user_pid), expanded: ""})}
    end
  end

  @impl true
  def handle_event("expand", %{"library_id" => library_id}, socket) do
    {
      :noreply,
      update(socket, :expanded, &(if &1 == library_id, do: "", else: library_id))
    }
  end

  @impl true
  def handle_event("import-library", _, socket) do

    socket =
      socket
      |> put_flash(:error, "This feature is currently not supported.")
    {:noreply, push_redirect(socket, to: ExportifyWeb.Router.Helpers.library_path(socket, :libraries))}
  end

  @impl true
  def handle_event("compare-library", %{"library_id" => library_id}, socket) do
    library_pids = socket.assigns.library_pids

    ExportifyLocalStorage.Library.start_import(library_pids, library_id)

    {:noreply, socket}
  end

  @impl true
  def handle_info({:update, _, _}, socket) do
    updated =
      socket
      |> update(:libraries, fn(_) -> get_libraries(ExportifyLocalStorage.Library.get_pid(socket.assigns["library_pids"], :user_pid)) end)
    {:noreply, updated}
  end

  @doc false
  defp get_libraries(user_pid) do
    case ExportifyLocalStorage.LibraryStorage.get_items(user_pid) do
      [] -> []
      [user|_] -> request_libraries(user)
    end
  end

  defp request_libraries(user) do
    spotify_id = user.spotify_id

    query =
      from l in ExportifyDatabase.Models.Library,
      join: u in ExportifyDatabase.Models.User,
      on: u.id == l.user_id,
      where: u.spotify_id == ^spotify_id,
      select: %{user_name: u.name, library_name: l.name, library_id: l.id, library_creation: l.creation}

    Repo.all(query)
  end

  @impl true
  def render(assigns) do
    Phoenix.View.render(ExportifyWeb.BackupView, "libraries.html", assigns)
  end
end
