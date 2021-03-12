defmodule API.Router do
  use API, :router

  pipeline :api do
    plug :accepts, ["json"]
  end

  pipeline :api_protected do
    plug :api
    plug API.Plugs.Authenticate
  end

  scope "/api", API do
    pipe_through :api

    get "/login", LoginController, :login
    get "/callback", LoginController, :callback
    #get "/library", LibraryController, :login

  end

  scope "/api", API do
    pipe_through :api_protected

    get "/auth", LoginController, :auth
    get "/albums/:id", AlbumController, :get_album
    get "/albums", AlbumController, :get_albums
    get "/albums/:id/tracks", AlbumController, :get_album_tracks

  end

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
      pipe_through [:fetch_session, :protect_from_forgery]
      live_dashboard "/dashboard", metrics: API.Telemetry
    end
  end
end
