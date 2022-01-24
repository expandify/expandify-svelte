import Config

config :spotify_ex, scopes: ["playlist-read-private", "playlist-read-collaborative", "user-library-read", "user-follow-read", "user-read-private", "&show_dialog=true"],
                    callback_url: "http://localhost:4000/api/authenticate"