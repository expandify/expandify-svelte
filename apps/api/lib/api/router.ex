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
  end


  scope "/api", API do
    pipe_through :api_protected

    get "/auth", LoginController, :auth

    scope "/albums", Album do
      get "/:id", AlbumController, :get_album
      get "/", AlbumController, :get_albums
      get "/:id/tracks", AlbumController, :get_album_tracks
    end

    scope "/artists", Artist do
      get "/:id", ArtistController, :get_artist
      get "/", ArtistController, :get_artists
      get "/:id/albums", ArtistController, :get_artists_albums
      get "/:id/related-artists", ArtistController, :get_related_artists
      get "/:id/top-tracks", ArtistController, :get_top_tracks
    end

    scope "/playlists", Playlist do
      get "/:id", PlaylistController, :get_playlist
      get "/:id/tracks", PlaylistController, :get_playlist_tracks
    end

    scope "/tracks", Track do
      get "/audio-features/:id", TrackController, :get_audio_features
      get "/audio-features", TrackController, :get_audio_features
      get "/:id", TrackController, :get_track
      get "/", TrackController, :get_tracks
      get "/:id/audio-analysis", TrackController, :get_audio_analysis
    end

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
