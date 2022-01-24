import Config

# We don't run a server during test. If one is required,
# you can enable the server option below.
config :api, Api.Endpoint,
  http: [ip: {127, 0, 0, 1}, port: 4002],
  secret_key_base: "Q1GVbvUya8ndXVurQMbttSRbeC1AxI4zYhX8YY52Xm4pTV0HKiaHqNbug1hh+Yce",
  server: false
