defmodule API.Track.TrackController do
  use API, :controller

  alias SpotifyCommunicator.Track

  def get_track(conn, %{"id" => id}) do
    {:ok, response} = Track.get_track(conn.assigns.claims["access_token"], id)
    json(conn, response)
  end

  def get_tracks(conn, %{"ids" => ids}) do
    {:ok, response} = Track.get_tracks(conn.assigns.claims["access_token"], ids: ids)
    json(conn, response)
  end

  def get_audio_features(conn, %{"id" => id}) do
    {:ok, response} = Track.audio_features(conn.assigns.claims["access_token"], id)
    json(conn, response)
  end

  def get_audio_features(conn, %{"ids" => ids}) do
    {:ok, response} = Track.audio_features(conn.assigns.claims["access_token"], ids: ids)
    json(conn, response)
  end

  def get_audio_analysis(conn, %{"id" => id}) do
    {:ok, response} = Track.audio_analysis(conn.assigns.claims["access_token"], id)
    json(conn, response)
  end
end
