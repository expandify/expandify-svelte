defmodule ExportifyDatabase.Models.Album do
  use ExportifyDatabase.Schema

  schema "albums" do
    field :name, :string
    field :spotify_id, :string
    field :image, :string
    has_many :songs, ExportifyDatabase.Models.Song, on_replace: :delete
    many_to_many :libraries, ExportifyDatabase.Models.Library, join_through: "libraries_albums"
    many_to_many :artists, ExportifyDatabase.Models.Artist, join_through: "artists_albums"
  end

  def changeset(album, params \\ %{}) do
    artists = album.artists

    album
    |> Ecto.Changeset.cast(params, [:name, :spotify_id, :image])
    |> Ecto.Changeset.validate_required([:name, :spotify_id])
    |> Ecto.Changeset.put_assoc(:artists, artists)
    |> Ecto.Changeset.foreign_key_constraint(:artists)
    |> Ecto.Changeset.unique_constraint([:spotify_id])
  end

end
