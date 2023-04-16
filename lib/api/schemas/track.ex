defmodule Track do
  use Api.Schema

  schema "tracks" do
    belongs_to :album, Album
    embeds_one :simple_album, TrackAlbum do
      field :total_tracks, :integer
      field :href, :string
      field :spotify_id, :string
      embeds_many :images, Image
      field :name, :string
      field :uri, :string
      embeds_one :artists, SimpleAlbumArtist do
        field :href, :string
        field :spotify_id, :string
        field :name, :string
        field :uri, :string
      end
    end
    many_to_many :artists, Artist, join_through: "artists_tracks"
    field :available_markets, {:array, :string}
    field :disc_number, :integer
    field :duration_ms, :integer
    field :explicit, :boolean
    embeds_one :external_ids, ExternalIds
    embeds_one :external_urls, ExternalUrls
    field :href, :string
    field :spotify_id, :string
    field :is_playable, :boolean
    embeds_one :restrictions, Restrictions
    field :name, :string
    field :popularity, :integer
    field :preview_url, :string
    field :track_number, :integer
    field :type, Ecto.Enum, values: [:track]
    field :uri, :string
    field :is_local, :boolean
  end

end
