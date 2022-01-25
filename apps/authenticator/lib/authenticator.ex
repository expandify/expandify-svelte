defmodule Exportify.Authenticator do

  def authenticate(params) do
    Spotify.Authentication.authenticate(%Spotify.Credentials{}, params)
    |> handle_authentication()
  end

  defp handle_authentication({:error, reason}), do: {:error, reason}
  defp handle_authentication({:ok, creds}) do
    Spotify.Profile.me(creds)
    |> safe_user(creds)
    |> create_token()
  end

  defp safe_user({:error, reason}, _), do: {:error, reason}
  defp safe_user({:ok, spotify_user}, creds) do
    %User{
      spotify_id: spotify_user.id,
      access_token: creds.access_token,
      refresh_token: creds.refresh_token
    }
    |> Users.Query.upsert()
  end

  defp create_token({:error, changeset}), do: {:error, changeset}
  defp create_token({:ok, user = %User{}}) do
    token = Phoenix.Token.sign(Api.Endpoint, "user auth", user.spotify_id)
    {:ok, token}
  end
end
