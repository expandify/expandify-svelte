defmodule Api.Repo.Migrations.CreateEpisodes do
  use Ecto.Migration

  def change do
    create table(:episodes, primary_key: false) do
      add :id, :uuid, primary_key: true, null: false

      add :audio_preview_url, :string
      add :description, :string
      add :html_description, :string
      add :duration_ms, :integer
      add :explicit, :boolean
      add :external_urls, :map
      add :href, :string
      add :spotify_id, :string
      add :images, {:array, :map}
      add :is_externally_hosted, :string
      add :is_playable, :boolean
      add :languages, {:array, :string}
      add :name, :string
      add :release_date, :string
      add :release_date_precision, :string
      add :resume_point, :map
      add :type, :string
      add :uri, :string
      add :restrictions, :map
      add :simple_show, :map
    end

    create unique_index(:episodes, [:spotify_id])
  end
end
