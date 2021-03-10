defmodule ExportifyDatabase.Models.Artist do
  use ExportifyDatabase.Schema

  schema "artists" do
    field :name, :string
    field :spotify_id, :string
    field :image, :string
    many_to_many :libraries, ExportifyDatabase.Models.Library, join_through: "libraries_artists"
    many_to_many :albums, ExportifyDatabase.Models.Album, join_through: "artists_albums", on_replace: :delete
    many_to_many :songs, ExportifyDatabase.Models.Song, join_through: "artists_songs", on_replace: :delete
  end

  def changeset(artist, params \\ %{}) do
    artist
    |> Ecto.Changeset.cast(params, [:name, :spotify_id, :image])
    |> Ecto.Changeset.validate_required([:name, :spotify_id])
    |> Ecto.Changeset.unique_constraint([:spotify_id])
  end
end
