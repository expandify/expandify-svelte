defmodule User do
  use Api.Schema

  schema "users" do
    field :country, :string
    field :display_name, :string
    field :email, :string
    embeds_one :explicit_content, ExplicitContent
    embeds_one :external_urls, ExternalUrls
    embeds_one :followers, Followers
    field :href, :string
    field :spotify_id, :string
    embeds_many :images, Image
    field :product, :string
    field :type, Ecto.Enum, values: [:user]
    field :uri, :string
  end
end
