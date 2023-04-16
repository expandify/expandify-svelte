defmodule ApiWeb.AlbumController do
  use ApiWeb, :controller
  use ApiWeb.CurrentUser

  def index(conn, _, current_user) do
    json(conn, "ID: #{current_user}")
  end

  def show(conn, %{"id" => id}, current_user) do
    json(conn, "#{id}: #{current_user}")
  end
end
