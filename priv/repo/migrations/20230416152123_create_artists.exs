defmodule Api.Repo.Migrations.CreateArtists do
  use Ecto.Migration

  def change do
    create table(:artists, primary_key: false) do
      add :id, :uuid, primary_key: true, null: false

      add :external_urls, :map
      add :followers, :map
      add :genres, {:array, :string}
      add :href, :string
      add :spotify_id, :string
      add :images, {:array, :map}
      add :name, :string
      add :popularity, :integer
      add :type, :string
      add :uri, :string
    end

    create unique_index(:artists, [:spotify_id])
  end
end
