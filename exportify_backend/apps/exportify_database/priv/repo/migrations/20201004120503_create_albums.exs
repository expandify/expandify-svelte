defmodule ExportifyDatabase.Repo.Migrations.CreateAlbums do
  use Ecto.Migration

  def change do
    create table(:albums) do
      add :name, :string
      add :spotify_id, :string
      add :image, :string
    end

    create unique_index(:albums, [:spotify_id])
  end
end
