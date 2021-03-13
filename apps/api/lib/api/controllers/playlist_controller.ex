defmodule API.Playlist.PlaylistController do
  use API, :controller

  alias SpotifyCommunicator.Playlist

  def get_playlist(conn, %{"id" => id}) do
    {:ok, response} = Playlist.get_playlist(conn.assigns.claims["access_token"], id)
    json(conn, response)
  end


  def get_playlist_tracks(conn, %{"id" => id}) do
    {:ok, response} = Playlist.get_playlist_tracks(conn.assigns.claims["access_token"], id)
    json(conn, response)
  end
end
