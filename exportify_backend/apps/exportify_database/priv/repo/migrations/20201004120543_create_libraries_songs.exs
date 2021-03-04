defmodule ExportifyDatabase.Repo.Migrations.CreateLibrariesSongs do
  use Ecto.Migration

  def change do
    create table(:libraries_songs) do
      add :library_id, references(:libraries)
      add :song_id, references(:songs)
    end

    create unique_index(:libraries_songs, [:library_id, :song_id])
  end
end
