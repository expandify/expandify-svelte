defmodule ExportifyDatabase.Repo.Migrations.CreatePlaylists do
  use Ecto.Migration

  def change do
    create table(:playlists) do
      add :name, :string
      add :description, :string
      add :spotify_id, :string
      add :song_count, :integer
    end

    create unique_index(:playlists, [:spotify_id])
  end
end
