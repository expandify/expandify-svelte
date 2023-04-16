defmodule Api.Repo.Migrations.CreateUsers do
  use Ecto.Migration

  def change do
    create table(:users, primary_key: false) do
      add :id, :uuid, primary_key: true, null: false

      add :country, :string
      add :display_name, :string
      add :email, :string
      add :explicit_content, :map
      add :external_urls, :map
      add :followers, :map
      add :href, :string
      add :spotify_id, :string
      add :images, {:array, :map}
      add :product, :string
      add :type, :string
      add :uri, :string
    end

    create unique_index(:users, [:spotify_id])
  end
end
