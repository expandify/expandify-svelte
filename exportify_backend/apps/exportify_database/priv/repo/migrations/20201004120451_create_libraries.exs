defmodule ExportifyDatabase.Repo.Migrations.CreateLibraries do
  use Ecto.Migration

  def change do
    create table(:libraries) do
      add :name, :string
      add :creation, :naive_datetime
      add :user_id, references(:users)
    end
  end
end
