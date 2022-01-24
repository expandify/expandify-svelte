defmodule Api.PlaylistController do
  @moduledoc false

  use Api, :controller

  def get_current_user_playlists(conn, _params) do
    response = conn.assigns.spotify_credentials
               |> Playlists.Service.get_current_user_playlists()

    case response do
      {:error, reason} ->
        conn
        |> put_status(:internal_server_error)
        |> json(%{reason: reason})
      {:ok, result} ->
        json(conn, result)
    end
  end
end
