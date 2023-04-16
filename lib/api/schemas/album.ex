defmodule Album do
  use Api.Schema

  schema "albums" do
    field :album_type, Ecto.Enum, values: [:album, :single, :compilation]
    field :total_tracks, :integer
    field :available_markets, {:array, :string}
    embeds_one :external_urls, ExternalUrls
    field :href, :string
    field :spotify_id, :string
    embeds_many :images, Image
    field :name, :string
    field :release_date, :string
    field :release_date_precision, :string
    embeds_one :restrictions, Restrictions
    field :type, Ecto.Enum, values: [:album]
    field :uri, :string
    embeds_many :copyrights, Copyright
    embeds_one :external_ids, ExternalIds
    field :genres, {:array, :string}
    field :label, :string
    field :popularity, :integer
    many_to_many :artists, Artist, join_through: "artists_albums"
    has_many :tracks, Track, foreign_key: :id
  end


end
