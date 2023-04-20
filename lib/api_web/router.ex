defmodule ApiWeb.Router do
  use ApiWeb, :router

  pipeline :api do
    plug :accepts, ["json"]
  end

  pipeline :auth do
    plug ApiWeb.Authentication
  end

  scope "/api", ApiWeb do
    pipe_through :api

    post("/login", LoginController, :login)

    pipe_through :auth

    scope "/sync" do
      get "/albums", SyncController, :get_sync_albums
      get "/artists", SyncController, :get_sync_artists
      get "/playlists", SyncController, :get_sync_playlists
      get "/tracks", SyncController, :get_sync_tracks

      post "/albums", SyncController, :sync_albums
      post "/artists", SyncController, :sync_artists
      post "/playlists", SyncController, :sync_playlists
      post "/tracks", SyncController, :sync_tracks
    end


    resources "/albums", AlbumController, only: [:index, :show]
    resources "/artists", ArtistController, only: [:index, :show]
    resources "/playlists", PlaylistController, only: [:index, :show]
    resources "/tracks", TrackController, only: [:index, :show]
    resources "/user", UserController, only: [:index]

  end

  # Enable LiveDashboard in development
  if Application.compile_env(:api, :dev_routes) do
    # If you want to use the LiveDashboard in production, you should put
    # it behind authentication and allow only admins to access it.
    # If your application does not have an admins-only section yet,
    # you can use Plug.BasicAuth to set up some basic authentication
    # as long as you are also using SSL (which you should anyway).
    import Phoenix.LiveDashboard.Router

    scope "/dev" do
      pipe_through [:fetch_session, :protect_from_forgery]

      live_dashboard "/dashboard", metrics: ApiWeb.Telemetry
    end
  end
end
