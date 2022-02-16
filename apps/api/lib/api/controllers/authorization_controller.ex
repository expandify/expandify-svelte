defmodule Api.AuthorizationController do
  @moduledoc false

  use Api, :controller

  def authorize(conn, _params), do: redirect(conn, external: Spotify.Authorization.url)

  def authenticate(conn, params) do
    user = Users.Token.authenticate(params)

    token = Phoenix.Token.sign(Api.Endpoint, "user auth", user.spotify_id)
    json(conn, %{token: token})
  end

end
