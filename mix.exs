defmodule Exportify.MixProject do
  use Mix.Project

  def project do
    [
      apps_path: "apps",
      apps: [
        :album_database,
        :api,
        :artist_database,
        :authenticator,
        :database_connector,
        :library_database,
        :playlist_database,
        :song_database,
        :spotify_communicator,
        :user_database
      ],
      version: "0.1.0",
      start_permanent: Mix.env() == :prod,
      deps: deps()
    ]
  end

  # Dependencies listed here are available only for this
  # project and cannot be accessed from applications inside
  # the apps folder.
  #
  # Run "mix help deps" for examples and options.
  defp deps do
    [
      {:credo, "~> 1.4", only: [:dev, :test], runtime: false}
    ]
  end
end
