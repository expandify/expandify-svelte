defmodule SimpleUser do
  use Ecto.Schema

  embedded_schema do
    embeds_one :external_urls, ExternalUrls
    embeds_one :followers, Followers
    field :href, :string
    field :spotify_id, :string
    field :type, Ecto.Enum, values: [:user]
    field :uri, :string
    field :display_name, :string
  end
end
