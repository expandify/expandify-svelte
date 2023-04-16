defmodule PlaylistTrack do
  use Api.Schema

  schema "playlist_tracks" do
    field :added_at, :date
    embeds_one :added_by, SimpleUser
    field :is_local, :boolean
    field :type, Ecto.Enum, values: [:episode, :track]
    belongs_to :track, Track
    belongs_to :episode, Episode
    belongs_to :playlist, Playlist
  end

end
