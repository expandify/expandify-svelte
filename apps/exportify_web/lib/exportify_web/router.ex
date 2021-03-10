defmodule ExportifyWeb.Router do
  use ExportifyWeb, :router

  pipeline :static_browser do
    plug :accepts, ["html"]
    plug :fetch_session
    plug :fetch_live_flash
    plug :put_root_layout, {ExportifyWeb.LayoutView, :root}
    plug :protect_from_forgery
    plug :put_secure_browser_headers
  end

  pipeline :live_browser do
    plug :accepts, ["html"]
    plug :fetch_session
    plug :fetch_live_flash
    plug :put_root_layout, {ExportifyWeb.LayoutView, :root}
    plug :protect_from_forgery
    plug :put_secure_browser_headers
  end

  pipeline :api do
    plug :accepts, ["json"]
  end

  scope "/", ExportifyWeb do
    pipe_through :static_browser

    get "/", IndexController, :index
    get "/login", IndexController, :login
    get "/callback", LoginController, :callback
    get "/download", DownloadController, :download
  end

  scope "/live", ExportifyWeb do
    pipe_through :live_browser

    live "/library", LibraryLive, :index
    live "/library/playlists", LibraryLive, :playlists
    live "/library/songs", LibraryLive, :songs
    live "/library/artists", LibraryLive, :artists
    live "/library/albums", LibraryLive, :albums
    live "/library/libraries", LibraryLive, :libraries
  end

  # Other scopes may use custom stacks.
  # scope "/api", ExportifyWeb do
  #   pipe_through :api
  # end

  # Enables LiveDashboard only for development
  #
  # If you want to use the LiveDashboard in production, you should put
  # it behind authentication and allow only admins to access it.
  # If your application does not have an admins-only section yet,
  # you can use Plug.BasicAuth to set up some basic authentication
  # as long as you are also using SSL (which you should anyway).
  if Mix.env() in [:dev, :test] do
    import Phoenix.LiveDashboard.Router

    scope "/" do
      pipe_through :static_browser
      live_dashboard "/dashboard", metrics: ExportifyWeb.Telemetry
    end
  end
end
