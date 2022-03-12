# This file is responsible for configuring your umbrella
# and **all applications** and their dependencies with the
# help of the Config module.
#
# Note that all applications in your umbrella share the
# same configuration and dependencies, which is why they
# all use the same configuration file. If you want different
# configurations or dependencies per app, it is best to
# move said applications out of the umbrella.
import Config

import_config "secret.exs"
import_config "spotify.exs"

config :user, User.Repo,
       database: "exportify",
       hostname: "localhost"
config :user, ecto_repos: [User.Repo]

config :playlist, Playlist.Repo,
       database: "exportify",
       hostname: "localhost"
config :playlist, ecto_repos: [Playlist.Repo]

config :song, Song.Repo,
       database: "exportify",
       hostname: "localhost"
config :song, ecto_repos: [Song.Repo]

config :artist, Artist.Repo,
       database: "exportify",
       hostname: "localhost"
config :artist, ecto_repos: [Artist.Repo]

config :album, Album.Repo,
       database: "exportify",
       hostname: "localhost"
config :album, ecto_repos: [Album.Repo]

config :api,
  generators: [context_app: false]

# Configures the endpoint
config :api, Api.Endpoint,
  url: [host: "localhost"],
  render_errors: [view: Api.ErrorView, accepts: ~w(json), layout: false],
  pubsub_server: Api.PubSub,
  live_view: [signing_salt: "ZeiBTFX8"]

# Sample configuration:
#
#     config :logger, :console,
#       level: :info,
#       format: "$date $time [$level] $metadata$message\n",
#       metadata: [:user_id]
#
# Configures Elixir's Logger
config :logger, :console,
  format: "$time $metadata[$level] $message\n",
  metadata: [:request_id]

# Use Jason for JSON parsing in Phoenix
config :phoenix, :json_library, Jason

# Import environment specific config. This must remain at the bottom
# of this file so it overrides the configuration defined above.
import_config "#{config_env()}.exs"
