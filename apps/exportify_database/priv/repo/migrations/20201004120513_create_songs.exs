defmodule ExportifyDatabase.Repo.Migrations.CreateSongs do
  use Ecto.Migration

  def change do
    create table(:songs) do
      add :name, :string
      add :duration, :string
      add :album_id, references(:albums)
      add :spotify_id, :string
    end

    create unique_index(:songs, [:spotify_id])
  end
end
