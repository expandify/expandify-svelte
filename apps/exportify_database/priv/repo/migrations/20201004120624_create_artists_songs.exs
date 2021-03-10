defmodule ExportifyDatabase.Repo.Migrations.CreateArtistsSongs do
  use Ecto.Migration

  def change do
    create table(:artists_songs) do
      add :artist_id, references(:artists)
      add :song_id, references(:songs)
    end

    create unique_index(:artists_songs, [:artist_id, :song_id])
  end
end
