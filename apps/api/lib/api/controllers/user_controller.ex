defmodule Api.UserController do
  @moduledoc false

  use Api, :controller


  def get_current_user(conn, _params) do
    try do
      conn.assigns.spotify_credentials
      |> Users.Service.get_current_user()
      |> build_response(conn)
    rescue
      e -> build_error(e, conn)
    end
  end

  def save_current_user(conn, _params) do
    try do
      conn.assigns.spotify_credentials
      |> Users.Service.save_current_user()
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
