defmodule Album.MixProject do
  use Mix.Project

  def project do
    [
      app: :album,
      version: "0.1.0",
      build_path: "../../_build",
      config_path: "../../config/config.exs",
      deps_path: "../../deps",
      lockfile: "../../mix.lock",
      elixir: "~> 1.13",
      start_permanent: Mix.env() == :prod,
      deps: deps()
    ]
  end

  # Run "mix help compile.app" to learn about applications.
  def application do
    [
      extra_applications: [:logger, :mongodb_ecto, :ecto],
      mod: {Album.Application, []}
    ]
  end

  # Run "mix help deps" to learn about dependencies.
  defp deps do
    [
      # {:dep_from_hexpm, "~> 0.3.0"},
      # {:dep_from_git, git: "https://github.com/elixir-lang/my_dep.git", tag: "0.1.0"},
      # {:sibling_app_in_umbrella, in_umbrella: true}
      {:mongodb, "~> 1.0.0-beta.1"},
      {:mongodb_ecto, "1.0.0-beta.2"},
      {:ecto, "~> 3.6"},
      {:spotify_ex, "~> 2.2.0"},
      {:spotify_helper, in_umbrella: true},
      {:user, in_umbrella: true}
    ]
  end
end
