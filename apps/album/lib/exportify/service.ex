defmodule Album.Service do
  @moduledoc false

  def get_saved_albums(credentials = %Spotify.Credentials{}) do
    Spotify.Album.my_albums_url(limit: 50)
    |> get_paging_response(credentials)
    |> Enum.map(&(Album.Query.convert(&1)))
    |> Enum.map(&(filter_keys(&1)))
  end

  def save_saved_albums(credentials = %Spotify.Credentials{}) do
    Spotify.Album.my_albums_url(limit: 50)
    |> get_paging_response(credentials)
    |> Enum.map(&(Album.Query.convert(&1)))
    |> Album.Query.save_all()
    |> Enum.map(&(filter_keys(&1)))
  end

  defp get_paging_response(nil, %Spotify.Credentials{}), do: []
  defp get_paging_response(url, credentials = %Spotify.Credentials{}) do
    response = credentials
               |> Spotify.Client.get(url)
               |> Spotify.Album.handle_response()
               |> SpotifyHelper.ResponseHandler.normalize_response()


    case response do
      :retry -> get_paging_response(url, credentials)
      :token_expired -> get_paging_response(url, User.Token.refresh(credentials))
      :empty -> []
      page -> page.items ++ get_paging_response(page.next, credentials)
    end
  end

  defp filter_keys(%Album{} = album) do
    album
    |> Map.from_struct()
    |> Map.drop([:__meta__])
  end

end
