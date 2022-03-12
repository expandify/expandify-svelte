defmodule User.Service do
  @moduledoc false

  def get_current_user(credentials = %Spotify.Credentials{}) do
    credentials
    |> get_spotify_user
    |> User.Query.convert(credentials)
    |> filter_keys
  end

  def save_current_user(credentials = %Spotify.Credentials{}) do
    credentials
    |> get_spotify_user
    |> User.Query.convert(credentials)
    |> User.Query.save()
    |> filter_keys
  end

  defp get_spotify_user(credentials = %Spotify.Credentials{}) do
    url = Spotify.Profile.me_url()

    response =
      Spotify.Client.get(credentials, url)
      |> Spotify.Profile.handle_response
      |> SpotifyHelper.ResponseHandler.normalize_response()

    case response do
      :retry -> get_current_user(credentials)
      :token_expired -> get_current_user(User.Token.refresh(credentials))
      :empty -> raise("Spotify did not return any user data, but also didnt throw an error.")
      spotify_user -> spotify_user
    end
  end

  defp filter_keys(user = %User{}) do
    Map.from_struct(user)
    |> Map.drop([:__meta__, :access_token, :refresh_token])
  end
end
