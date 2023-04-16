defmodule Api.Repo.Migrations.CreateArtistsAlbums do
  use Ecto.Migration

  def change do
    create table(:artists_albums) do
      add :artist_id, references(:artists, type: :uuid)
      add :album_id, references(:albums, type: :uuid)
    end

    create unique_index(:artists_albums, [:artist_id, :album_id])
  end
end
