defmodule Api.Repo.Migrations.CreateAlbums do
  use Ecto.Migration

  def change do
    create table(:albums, primary_key: false) do
      add :id, :uuid, primary_key: true, null: false

      add :album_type, :string
      add :total_tracks, :integer
      add :available_markets, {:array, :string}
      add :external_urls, :map
      add :href, :string
      add :spotify_id, :string
      add :images, {:array, :map}
      add :name, :string
      add :release_date, :string
      add :release_date_precision, :string
      add :restrictions, :map
      add :type, :string
      add :uri, :string
      add :copyrights, {:array, :map}
      add :external_ids, :map
      add :genres, {:array, :string}
      add :label, :string
      add :popularity, :integer
    end

    create unique_index(:albums, [:spotify_id])
  end
end
