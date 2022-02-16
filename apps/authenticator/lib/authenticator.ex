defmodule Exportify.Authenticator do

  def authenticate(params) do
    # This function gets called after the spotify redirect
    # This gets the access and refresh token from spotify
    Spotify.Authentication.authenticate(%Spotify.Credentials{}, params)
    |> save_user()
    |> create_token()
  end

  def refresh(%Spotify.Credentials{} = creds) do
    user =
      Spotify.Authentication.refresh(creds)
      |> save_user()

    %Spotify.Credentials{access_token: user.access_token, refresh_token: user.refresh_token}
  end

  defp create_token(user) do
    Phoenix.Token.sign(Api.Endpoint, "user auth", user.spotify_id)
  end

  defp save_user({:error, reason}), do: raise(reason)
  defp save_user({:ok, creds}), do: Users.Service.save_current_user(creds)
end
