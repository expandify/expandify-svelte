defmodule Api.Repo.Migrations.CreatePlaylistTracks do
  use Ecto.Migration

  def change do
    create table(:playlist_tracks, primary_key: false) do
      add :id, :uuid, primary_key: true, null: false

      add :added_at, :date
      add :added_by, :map
      add :is_local, :boolean
      add :type, :string
      add :track, references(:tracks, type: :uuid)
      add :episode, references(:episodes, type: :uuid)
      add :playlist, references(:playlists, type: :uuid)
    end
  end
end
