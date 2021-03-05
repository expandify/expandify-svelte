defmodule UserDatabase.Repo do
  use DatabaseConnector.Query
  use Ecto.Repo,
    otp_app: :user_database,
    adapter: Ecto.Adapters.MyXQL
end
