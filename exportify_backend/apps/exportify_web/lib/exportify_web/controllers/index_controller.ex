defmodule ExportifyWeb.IndexController do
  @moduledoc """
    Responsible for the initial page of the app.
  """
  use ExportifyWeb, :controller

  @doc """
    Renders the html of the index page.
  """
  def index(conn, _params) do
    render(conn, "index.html")
  end

end
