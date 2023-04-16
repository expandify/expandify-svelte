defmodule Show do
  use Api.Schema

  schema "shows" do
    field :available_markets, {:array, :string}
    embeds_many :copyrights, Copyright
    field :description, :string
    field :html_description, :string
    field :explicit, :boolean
    embeds_one :external_urls, ExternalUrls
    field :href, :string
    field :spotify_id, :string
    embeds_many :images, Image
    field :is_externally_hosted, :string
    field :languages, {:array, :string}
    field :media_type, :string
    field :name, :string
    field :publisher, :string
    field :type, Ecto.Enum, values: [:show]
    field :uri, :string
    field :total_episodes, :integer
    has_many :episodes, Episode, foreign_key: :id
  end

end
