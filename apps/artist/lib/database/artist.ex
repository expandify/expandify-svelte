defmodule Artist do
  @moduledoc false
  use Ecto.Schema

  @primary_key {:id, :binary_id, autogenerate: true}
  schema "artists" do
    field :spotify_id, :string
    field :external_urls, :map
    field :followers, :map
    field :genres, {:array, :string}
    field :href, :string
    field :images, {:array, :map}
    field :name, :string
    field :popularity, :integer
    field :type, :string
    field :uri, :string
  end
end
