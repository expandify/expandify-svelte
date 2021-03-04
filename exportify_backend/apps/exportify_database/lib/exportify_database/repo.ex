defmodule ExportifyDatabase.Repo do
  use Ecto.Repo,
    otp_app: :exportify_database,
    adapter: Ecto.Adapters.MyXQL
end
