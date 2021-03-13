defmodule API.Album.AlbumController do
  use API, :controller

  alias SpotifyCommunicator.Album

  def get_album(conn, %{"id" => id}) do
    {:ok, response} = Album.get_album(conn.assigns.claims["access_token"], id)
    json(conn, response)
  end

  def get_albums(conn, %{"ids" => ids}) do
    {:ok, response} = Album.get_albums(conn.assigns.claims["access_token"], ids: ids)
    json(conn, response)
  end

  def get_album_tracks(conn, %{"id" => id}) do
    {:ok, response} = Album.get_album_tracks(conn.assigns.claims["access_token"], id)
    json(conn, response)
  end
end
