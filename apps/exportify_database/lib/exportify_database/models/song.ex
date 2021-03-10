defmodule ExportifyDatabase.Models.Song do
  use ExportifyDatabase.Schema

  schema "songs" do
    field :name, :string
    field :duration, :string
    field :spotify_id, :string
    many_to_many :artists, ExportifyDatabase.Models.Artist, join_through: "artists_songs"
    many_to_many :libraries, ExportifyDatabase.Models.Library, join_through: "libraries_songs"
    many_to_many :playlists, ExportifyDatabase.Models.Playlist, join_through: "playlists_songs"
    belongs_to :album, ExportifyDatabase.Models.Album, on_replace: :update
  end

  def changeset(song, params \\ %{}) do
    artists = song.artists
    album = song.album

    song
    |> Ecto.Changeset.cast(params, [:name, :spotify_id, :duration])
    |> Ecto.Changeset.validate_required([:name, :spotify_id, :duration])
    |> Ecto.Changeset.put_assoc(:artists, artists)
    |> Ecto.Changeset.put_assoc(:album, album)
    |> Ecto.Changeset.foreign_key_constraint(:artists)
    |> Ecto.Changeset.foreign_key_constraint(:album)
    |> Ecto.Changeset.unique_constraint([:spotify_id])
  end


end
