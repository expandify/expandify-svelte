defmodule Artist do
  use Api.Schema

  schema "artists" do
    embeds_one :external_urls, ExternalUrls
    embeds_one :followers, Followers
    field :genres, {:array, :string}
    field :href, :string
    field :spotify_id, :string
    embeds_many :images, Image
    field :name, :string
    field :popularity, :integer
    field :type, Ecto.Enum, values: [:artist]
    field :uri, :string
  end
end
