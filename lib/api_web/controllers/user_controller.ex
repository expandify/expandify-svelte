defmodule ApiWeb.UserController do
  use ApiWeb, :controller
  use ApiWeb.CurrentUser

  def index(conn, _, current_user) do
    json(conn, "ID: #{current_user}")
  end
end
