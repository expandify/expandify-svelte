defmodule Api.Repo.Migrations.CreateTracksArtists do
  use Ecto.Migration

  def change do
    create table(:tracks_artists) do
      add :artist_id, references(:artists, type: :uuid)
      add :track_id, references(:tracks, type: :uuid)
    end

    create unique_index(:tracks_artists, [:artist_id, :track_id])
  end
end
