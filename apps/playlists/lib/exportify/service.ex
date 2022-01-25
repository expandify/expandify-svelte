defmodule Playlists.Service do
  @moduledoc false

  def get_current_user_playlists(credentials = %Spotify.Credentials{}) do
    Spotify.Playlist.get_current_user_playlists_url()
    |> get_paging_response(credentials)
    |> Enum.map(&(Map.from_struct(&1)))
  end

  def save_current_user_playlists(credentials = %Spotify.Credentials{}) do
    Spotify.Playlist.get_current_user_playlists_url()
    |> get_paging_response(credentials)
    |> Playlists.Query.save_all()
  end

  defp get_paging_response(nil, %Spotify.Credentials{}), do: []
  defp get_paging_response(url, credentials = %Spotify.Credentials{}) do
    response = credentials
               |> Spotify.Client.get(url)
               |> Spotify.Playlist.handle_response()
               |> SpotifyResponseHandler.normalize_response()

    case response do
      {:empty, _} -> []
      {:error, reason} -> raise(reason)
      {:token_expired, _} -> raise("Token expired")
      #TODO
      {:retry, retry_after} ->
        Process.sleep(retry_after * 1000)
        get_paging_response(url, credentials)
      {:ok, page} -> page.items ++ get_paging_response(page.next, credentials)
    end
  end

end
