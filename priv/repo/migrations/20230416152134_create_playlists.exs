defmodule Api.Repo.Migrations.CreatePlaylists do
  use Ecto.Migration

  def change do
    create table(:playlists, primary_key: false) do
      add :id, :uuid, primary_key: true, null: false

      add :collaborative, :boolean
      add :description, :string
      add :external_urls, :map
      add :followers, :map
      add :href, :string
      add :spotify_id, :string
      add :images, {:array, :map}
      add :name, :string
      add :owner, :map
      add :public, :boolean
      add :snapshot_id, :string
      add :total_tracks, :integer
      add :type, :string
      add :uri, :string
    end

    create unique_index(:playlists, [:spotify_id, :snapshot_id])
  end
end
