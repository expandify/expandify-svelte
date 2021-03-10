defmodule ExportifyDatabase.Models.Playlist do
  use ExportifyDatabase.Schema

  schema "playlists" do
    field :name, :string
    field :description, :string
    field :spotify_id, :string
    field :song_count, :integer
    many_to_many :songs, ExportifyDatabase.Models.Song, join_through: "playlists_songs"
    many_to_many :libraries, ExportifyDatabase.Models.Library, join_through: "libraries_playlists"
  end

  def changeset(playlist, params \\ %{}) do
    songs = playlist.songs

    playlist
    |> Ecto.Changeset.cast(params, [:name, :spotify_id, :description, :song_count])
    |> Ecto.Changeset.validate_required([:name, :spotify_id, :song_count])
    |> Ecto.Changeset.put_assoc(:songs, songs)
    |> Ecto.Changeset.unique_constraint([:spotify_id])
    |> Ecto.Changeset.foreign_key_constraint(:songs)
  end


end
