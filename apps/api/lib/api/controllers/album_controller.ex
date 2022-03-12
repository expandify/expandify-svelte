defmodule Api.AlbumController do
  @moduledoc false

  use Api, :controller

  def get_saved_albums(conn, _params) do
    try do
      conn.assigns.spotify_credentials
      |> Album.Service.get_saved_albums()
      |> build_response(conn)
    rescue
      e -> build_error(e, conn)
    end
  end

  def save_saved_albums(conn, _params) do
    try do
      conn.assigns.spotify_credentials
      |> Album.Service.save_saved_albums()
      |> build_response(conn)
    rescue
      e -> build_error(e, conn)
    end
  end

  defp build_response(result, conn), do: json(conn, result)
  defp build_error(reason, conn) do
    conn
    |> put_status(:internal_server_error)
    |> json(%{reason: reason})
  end

end
