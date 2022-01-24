defmodule Playlists.Service do
  @moduledoc false

  def get_current_user_playlists(credentials = %Spotify.Credentials{}) do
    Spotify.Playlist.get_current_user_playlists(credentials)
    |> paging_to_map
  end

  defp paging_to_map({:error, reason}), do: {:error, reason}
  defp paging_to_map({:ok, paging = %Spotify.Paging{}}) do
    map = Map.from_struct(paging)
    items = Enum.map(map.items, fn x -> Map.from_struct(x) end)
    {:ok, Map.put(map, :items, items)}
  end

end
