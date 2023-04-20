defmodule Api.Repo.Migrations.CreateUsers do
  use Ecto.Migration

  def change do
    create table(:users, primary_key: false) do
      add :id, :uuid, primary_key: true, null: false

      add :access_token, :string
      add :refresh_token, :string
      add :spotify_user_id, references(:spotify_users, type: :uuid)
    end
  end
end
