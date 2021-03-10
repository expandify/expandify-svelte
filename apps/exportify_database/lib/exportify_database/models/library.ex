defmodule ExportifyDatabase.Models.Library do
  use ExportifyDatabase.Schema

  schema "libraries" do
    field :name, :string
    field :creation, :naive_datetime
    many_to_many :albums, ExportifyDatabase.Models.Album, join_through: "libraries_albums"
    many_to_many :artists, ExportifyDatabase.Models.Artist, join_through: "libraries_artists"
    many_to_many :songs, ExportifyDatabase.Models.Song, join_through: "libraries_songs"
    many_to_many :playlists, ExportifyDatabase.Models.Playlist, join_through: "libraries_playlists"
    belongs_to :user, ExportifyDatabase.Models.User
  end

  def changeset(library, params \\ %{}) do
    songs = library.songs
    albums = library.albums
    artists = library.artists
    playlists = library.playlists
    user = library.user

    library
    |> Ecto.Changeset.cast(params, [:name, :creation])
    |> Ecto.Changeset.validate_required([:creation, :user])
    |> Ecto.Changeset.put_assoc(:songs, songs)
    |> Ecto.Changeset.put_assoc(:artists, artists)
    |> Ecto.Changeset.put_assoc(:albums, albums)
    |> Ecto.Changeset.put_assoc(:playlists, playlists)
    |> Ecto.Changeset.put_assoc(:user, user)
    |> Ecto.Changeset.foreign_key_constraint(:user)
  end
end
