defmodule Api.Repo.Migrations.CreateTracks do
  use Ecto.Migration

  def change do
    create table(:tracks, primary_key: false) do
      add :id, :uuid, primary_key: true, null: false

      add :album, references(:albums, type: :uuid)
      add :simple_album, :map
      add :available_markets, {:array, :string}
      add :disc_number, :integer
      add :duration_ms, :integer
      add :explicit, :boolean
      add :external_ids, :map
      add :external_urls, :map
      add :href, :string
      add :spotify_id, :string
      add :is_playable, :boolean
      add :restrictions, :map
      add :name, :string
      add :popularity, :integer
      add :preview_url, :string
      add :track_number, :integer
      add :type, :string
      add :uri, :string
      add :is_local, :boolean
    end

    create unique_index(:tracks, [:spotify_id])
  end
end
