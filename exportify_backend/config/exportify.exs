import Config

spotify_client_id =
  System.get_env("SPOTIFY_CLIENT_ID") ||
    raise """
    Environment variable not found.
    Please set the environment variable 'SPOTIFY_CLIENT_ID'.
    """


spotify_client_secret =
  System.get_env("SPOTIFY_CLIENT_SECRET") ||
    raise """
    Environment variable not found.
    Please set the environment variable 'SPOTIFY_CLIENT_SECRET'.
    """

database_host = System.get_env("DATABASE_HOST", "localhost")
database_name = System.get_env("DATABASE_NAME", "Exportify")
database_user = System.get_env("DATABASE_USER", "Exportify")
database_password = System.get_env("DATABASE_PASSWORD", "")


jwt_secret =
  System.get_env("JOKEN_SECRET") ||
    raise """
    Environment variable not found.
    Please set the environment variable 'JOKEN_SECRET'.
    """

if database_password == "" do
    IO.puts(:stderr, """
    WARNING: The environment variable DATABASE_PASSWORD is empty. The database
    connection will default to an empty password. This is very insecure and not
    recommended for production use.
    """)
end


config :exportify_web, ExportifyWeb.Endpoint,
  spotify_client_id: spotify_client_id,
  spotify_client_secret: spotify_client_secret


config :exportify_database, ExportifyDatabase.Repo,
    hostname: database_host,
    database: database_name,
    username: database_user,
    password: database_password

config :exportify_database,
       ecto_repos: [ExportifyDatabase.Repo]

# Signer is needed for the Jason Web Token
config :joken, default_signer: jwt_secret
