defmodule Users.Service do
  @moduledoc false

  def get_current_user(credentials = %Spotify.Credentials{}) do
    credentials
    |> get_spotify_user
    |> Users.Query.convert(credentials)
    |> filter_keys
  end

  def save_current_user(credentials = %Spotify.Credentials{}) do
    credentials
    |> get_spotify_user
    |> Users.Query.convert(credentials)
    |> Users.Query.save()
    |> filter_keys
  end

  defp get_spotify_user(credentials = %Spotify.Credentials{}) do
    url = Spotify.Profile.me_url()

    response =
      Spotify.Client.get(credentials, url)
      |> Spotify.Profile.handle_response
      |> SpotifyHelper.ResponseHandler.normalize_response(credentials)

    case response do
      {:empty, _} -> raise("Spotify did not return any user data, but also didnt throw an error.")
      {:retry_with, new_creds} -> get_current_user(new_creds)
      {:ok, spotify_user} -> spotify_user
    end
  end

  defp filter_keys(user = %User{}) do
    Map.from_struct(user)
    |> Map.drop([:__meta__, :access_token, :refresh_token])
  end
end
