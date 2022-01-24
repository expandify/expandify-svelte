defmodule Api.AuthorizationController do
  @moduledoc false

  use Api, :controller



  def authorize(conn, _params), do: redirect(conn, external: Spotify.Authorization.url)

  def authenticate(conn, params) do
    params
    |> Exportify.Authenticator.authenticate()
    |> authentication_response(conn)
  end

  defp authentication_response({:ok, token}, conn), do: json(conn, %{token: token})
  defp authentication_response({:error, reason}, conn), do: conn |> put_status(:internal_server_error) |> json(%{reason: reason})


end
