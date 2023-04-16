defmodule Api.Repo.Migrations.CreateShows do
  use Ecto.Migration

  def change do
    create table(:shows, primary_key: false) do
      add :id, :uuid, primary_key: true, null: false

      add :available_markets, {:array, :string}
      add :copyrights, {:array, :map}
      add :description, :string
      add :html_description, :string
      add :explicit, :boolean
      add :external_urls, :map
      add :href, :string
      add :spotify_id, :string
      add :images, {:array, :map}
      add :is_externally_hosted, :string
      add :languages, {:array, :string}
      add :media_type, :string
      add :name, :string
      add :publisher, :string
      add :type, :string
      add :uri, :string
      add :total_episodes, :integer
    end

    create unique_index(:shows, [:spotify_id])
  end
end
