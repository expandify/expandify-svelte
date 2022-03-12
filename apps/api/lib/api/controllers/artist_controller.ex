defmodule Api.ArtistController do
  @moduledoc false

  use Api, :controller

  def get_followed_artists(conn, _params) do
    try do
      conn.assigns.spotify_credentials
      |> Artist.Service.get_followed_artists()
      |> build_response(conn)
    rescue
      e -> build_error(e, conn)
    end
  end

  def save_followed_artists(conn, _params) do
    try do
      conn.assigns.spotify_credentials
      |> Artist.Service.save_followed_artists()
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
