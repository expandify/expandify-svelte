defmodule ExportifyDatabase.Repo.Migrations.CreateUsers do
  use Ecto.Migration

  def change do
    create table(:users) do
      add :name, :string
      add :access_token, :string
      add :refresh_token, :string
      add :spotify_id, :string
    end

    create unique_index(:users, [:spotify_id])
  end
end
