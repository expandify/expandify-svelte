defmodule Artist.Service do
  @moduledoc false

  def get_followed_artists(credentials = %Spotify.Credentials{}) do
    Spotify.Artist.artists_I_follow_url(limit: 50)
    |> get_paging_response(credentials)
    |> Enum.map(&(Artist.Query.convert(&1)))
    |> Enum.map(&(filter_keys(&1)))
  end

  def save_followed_artists(credentials = %Spotify.Credentials{}) do
    Spotify.Artist.artists_I_follow_url(limit: 50)
    |> get_paging_response(credentials)
    |> Enum.map(&(Artist.Query.convert(&1)))
    |> Artist.Query.save_all()
    |> Enum.map(&(filter_keys(&1)))
  end

  defp get_paging_response(nil, %Spotify.Credentials{}), do: []
  defp get_paging_response(url, credentials = %Spotify.Credentials{}) do
    response = credentials
               |> Spotify.Client.get(url)
               |> Spotify.Artist.handle_response()
               |> SpotifyHelper.ResponseHandler.normalize_response()

    case response do
      :retry -> get_paging_response(url, credentials)
      :token_expired -> get_paging_response(url, User.Token.refresh(credentials))
      :empty -> []
      page -> page.items ++ get_paging_response(page.next, credentials)
    end
  end

  defp filter_keys(%Artist{} = artist) do
    artist
    |> Map.from_struct()
    |> Map.drop([:__meta__])
  end

end
