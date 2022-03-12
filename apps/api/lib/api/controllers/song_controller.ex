defmodule Api.SongController do
  @moduledoc false

  use Api, :controller

  def get_saved_songs(conn, _params) do
    try do
      result =
      conn.assigns.spotify_credentials
      |> Song.Service.get_saved_songs()
      result
      |> build_response(conn)
    rescue
      e -> build_error(e, conn)
    end
  end

  def save_saved_songs(conn, _params) do
    try do
      conn.assigns.spotify_credentials
      |> Song.Service.save_saved_songs()
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
