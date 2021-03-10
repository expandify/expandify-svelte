defmodule ExportifyDatabase.Repo.Migrations.CreateLibrariesAlbums do
  use Ecto.Migration

  def change do
    create table(:libraries_albums) do
      add :library_id, references(:libraries)
      add :album_id, references(:albums)
    end

    create unique_index(:libraries_albums, [:library_id, :album_id])
  end
end
