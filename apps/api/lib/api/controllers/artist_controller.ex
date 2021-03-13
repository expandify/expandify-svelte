defmodule API.Artist.ArtistController do
  use API, :controller

  alias SpotifyCommunicator.Artist

  def get_artist(conn, %{"id" => id}) do
    {:ok, response} = Artist.get_artist(conn.assigns.claims["access_token"], id)
    json(conn, response)
  end

  def get_artists(conn, %{"ids" => ids}) do
    {:ok, response} = Artist.get_artists(conn.assigns.claims["access_token"], ids: ids)
    json(conn, response)
  end

  def get_artists_albums(conn, %{"id" => id}) do
    {:ok, response} = Artist.get_artists_albums(conn.assigns.claims["access_token"], id)
    json(conn, response)
  end

  def get_related_artists(conn, %{"id" => id}) do
    {:ok, response} = Artist.get_related_artists(conn.assigns.claims["access_token"], id)
    json(conn, response)
  end

  def get_top_tracks(conn, %{"id" => id} = params) do
    {:ok, response} = Artist.get_top_tracks(conn.assigns.claims["access_token"], id, %{"market" => Map.get(params, "market", "DE")})
    json(conn, response)
  end

end
