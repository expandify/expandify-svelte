defmodule ExportifyDatabase.Repo.Migrations.CreateLibrariesArtists do
  use Ecto.Migration

  def change do
    create table(:libraries_artists) do
      add :library_id, references(:libraries)
      add :artist_id, references(:artists)
    end

    create unique_index(:libraries_artists, [:library_id, :artist_id])
  end
end
