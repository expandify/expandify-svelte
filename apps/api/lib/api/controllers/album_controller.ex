defmodule API.AlbumController do
  use API, :controller

  alias SpotifyCommunicator.Album

  def get_album(conn, %{"id" => id}) do
    {:ok, response} = Album.get_album(conn.assigns.claims["access_token"], id)


    # TODO: Poison vs Jason.
    # TODO: Are structs really helpful? Maybe just keep everything as json


    json(conn, Poison.decode!(response))
  end

  def get_albums(conn, %{"ids" => ids}) do
    {:ok, response} = Album.get_albums(conn.assigns.claims["access_token"], ids)
    json(conn, Poison.decode!(response))
  end

  def get_album_tracks(conn, %{"id" => id}) do
    {:ok, response} = Album.get_album_tracks(conn.assigns.claims["access_token"], id)
    json(conn, Poison.decode!(response))
  end
end
