defmodule Playlist.Service do
  @moduledoc false

  def get_current_user_playlists(credentials = %Spotify.Credentials{}) do
    Spotify.Playlist.get_current_user_playlists_url()
    |> get_paging_response(credentials)
    |> Enum.map(&(Playlist.Query.convert(&1)))
    |> Enum.map(&(filter_keys(&1)))
  end

  def save_current_user_playlists(credentials = %Spotify.Credentials{}) do
    Spotify.Playlist.get_current_user_playlists_url()
    |> get_paging_response(credentials)
    |> Enum.map(&(Playlist.Query.convert(&1)))
    |> Playlist.Query.save_all()
    |> Enum.map(&(filter_keys(&1)))
  end

  defp get_paging_response(nil, %Spotify.Credentials{}), do: []
  defp get_paging_response(url, credentials = %Spotify.Credentials{}) do
    response = credentials
               |> Spotify.Client.get(url)
               |> Spotify.Playlist.handle_response()
               |> SpotifyHelper.ResponseHandler.normalize_response()

    case response do
      :retry -> get_paging_response(url, credentials)
      :token_expired -> get_paging_response(url, User.Token.refresh(credentials))
      :empty -> []
      page -> page.items ++ get_paging_response(page.next, credentials)
    end
  end

  defp filter_keys(%Playlist{} = playlist) do
    playlist
    |> Map.from_struct()
    |> Map.drop([:__meta__])
  end

end
