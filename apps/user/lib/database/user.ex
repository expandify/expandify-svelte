defmodule User do
  @moduledoc false
  use Ecto.Schema

  @primary_key {:id, :binary_id, autogenerate: true}
  schema "users" do
    field :spotify_id, :string
    field :access_token, :string
    field :refresh_token, :string
    field :country, :string
    field :display_name, :string
    field :email, :string
    field :external_urls, :map
    field :followers, :map
    field :href, :string
    field :images, {:array, :map}
    field :product, :string
    field :type, :string
    field :uri, :string
  end
end
