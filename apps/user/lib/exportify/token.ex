defmodule User.Token do
  @moduledoc false

  def authenticate(params) do
    # This function gets called after the spotify redirect
    # This gets the access and refresh token from spotify
    Spotify.Authentication.authenticate(%Spotify.Credentials{}, params)
    |> save_user()
  end

  def  refresh(%Spotify.Credentials{} = creds) do
    user =
      Spotify.Authentication.refresh(creds)
      |> save_user()

    %Spotify.Credentials{access_token: user.access_token, refresh_token: user.refresh_token}
  end


  defp save_user({:error, reason}), do: raise(reason)
  defp save_user({:ok, creds}), do: User.Service.save_current_user(creds)

end
