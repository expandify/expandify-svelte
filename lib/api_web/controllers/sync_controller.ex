defmodule ApiWeb.SyncController do
  use ApiWeb, :controller
  use ApiWeb.CurrentUser

  def get_sync_albums(conn, _, current_user) do
    json(conn, "Current User: #{current_user}")
  end

  def get_sync_artists(conn, _, current_user) do
    json(conn, "Current User: #{current_user}")
  end

  def get_sync_playlists(conn, _, current_user) do
    json(conn, "Current User: #{current_user}")
  end

  def get_sync_tracks(conn, _, current_user) do
    json(conn, "Current User: #{current_user}")
  end

  def sync_albums(conn, _, current_user) do
    json(conn, "Current User: #{current_user}")
  end

  def sync_artists(conn, _, current_user) do
    json(conn, "Current User: #{current_user}")
  end

  def sync_playlists(conn, _, current_user) do
    json(conn, "Current User: #{current_user}")
  end

  def sync_tracks(conn, _, current_user) do
    json(conn, "Current User: #{current_user}")
  end

end
