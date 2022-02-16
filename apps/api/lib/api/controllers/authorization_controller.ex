defmodule Api.AuthorizationController do
  @moduledoc false

  use Api, :controller

  def authorize(conn, _params), do: redirect(conn, external: Spotify.Authorization.url)

  def authenticate(conn, params) do
    token = Exportify.Authenticator.authenticate(params)

    json(conn, %{token: token})
  end

end
