defmodule Playlist do
  use Api.Schema

  schema "playlists" do
    field :collaborative, :boolean
    field :description, :string
    embeds_one :external_urls, ExternalUrls
    embeds_one :followers, Followers
    field :href, :string
    field :spotify_id, :string
    embeds_many :images, Image
    field :name, :string
    embeds_one :owner, SimpleUser
    field :public, :boolean
    field :snapshot_id, :string
    field :total_tracks, :integer
    has_many :tracks, PlaylistTrack, foreign_key: :id
    field :type, Ecto.Enum, values: [:playlist]
    field :uri, :string
  end
end
