defmodule Song.Service do
  @moduledoc false

  def get_saved_songs(credentials = %Spotify.Credentials{}) do
    Spotify.Library.saved_tracks_url(limit: 50)
    |> get_paging_response(credentials)
    |> Enum.map(&(Song.Query.convert(&1)))
    |> Enum.map(&(filter_keys(&1)))
  end

  def save_saved_songs(credentials = %Spotify.Credentials{}) do
    Spotify.Library.saved_tracks_url(limit: 50)
    |> get_paging_response(credentials)
    |> Enum.map(&(Song.Query.convert(&1)))
    |> Song.Query.save_all()
    |> Enum.map(&(filter_keys(&1)))
  end

  defp get_paging_response(nil, %Spotify.Credentials{}), do: []
  defp get_paging_response(url, credentials = %Spotify.Credentials{}) do
    response = credentials
               |> Spotify.Client.get(url)
               |> Spotify.Library.handle_response()
               |> SpotifyHelper.ResponseHandler.normalize_response()

    case response do
      :retry -> get_paging_response(url, credentials)
      :token_expired -> get_paging_response(url, User.Token.refresh(credentials))
      :empty -> []
      page -> page.items ++ get_paging_response(page.next, credentials)
    end
  end

  defp filter_keys(%Song{} = song) do
    song
    |> Map.from_struct()
    |> Map.drop([:__meta__])
  end

end