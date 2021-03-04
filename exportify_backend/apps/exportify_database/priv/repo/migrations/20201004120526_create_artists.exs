defmodule ExportifyDatabase.Repo.Migrations.CreateArtists do
  use Ecto.Migration

  def change do
    create table(:artists) do
      add :name, :string
      add :spotify_id, :string
      add :image, :string
    end

    create unique_index(:artists, [:spotify_id])
  end
end
