defmodule Playlists.Service do
  @moduledoc false

  def get_current_user_playlists(credentials = %Spotify.Credentials{}) do
    Spotify.Playlist.get_current_user_playlists_url()
    |> get_paging_response(credentials)
    |> Enum.map(&(Playlists.Query.convert(&1)))
    |> Enum.map(&(filter_keys(&1)))
  end

  def save_current_user_playlists(credentials = %Spotify.Credentials{}) do
    Spotify.Playlist.get_current_user_playlists_url()
    |> get_paging_response(credentials)
    |> Enum.map(&(Playlists.Query.convert(&1)))
    |> Playlists.Query.save_all()
    |> Enum.map(&(filter_keys(&1)))
  end

  defp get_paging_response(nil, %Spotify.Credentials{}), do: []
  defp get_paging_response(url, credentials = %Spotify.Credentials{}) do
    response = credentials
               |> Spotify.Client.get(url)
               |> Spotify.Playlist.handle_response()
               |> SpotifyResponseHandler.normalize_response(credentials)

    case response do
      {:empty, _} -> []
      {:retry_with, new_creds} -> get_paging_response(url, new_creds)
      {:ok, page} -> page.items ++ get_paging_response(page.next, credentials)
    end
  end

  defp filter_keys(%Playlist{} = playlist) do
    playlist
    |> Map.from_struct()
    |> Map.drop([:__meta__])
  end

end
