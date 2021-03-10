defmodule ExportifyDatabase.Repo.Migrations.CreateLibrariesPlaylists do
  use Ecto.Migration

  def change do
    create table(:libraries_playlists) do
      add :library_id, references(:libraries)
      add :playlist_id, references(:playlists)
    end

    create unique_index(:libraries_playlists, [:library_id, :playlist_id])
  end
end
