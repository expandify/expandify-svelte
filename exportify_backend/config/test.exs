use Mix.Config

# We don't run a server during test. If one is required,
# you can enable the server option below.
config :exportify_api, ExportifyApi.Endpoint,
  http: [port: 4002],
  server: false


# We don't run a server during test. If one is required,
# you can enable the server option below.
config :exportify_web, ExportifyWeb.Endpoint,
  http: [port: 4003],
  server: false
