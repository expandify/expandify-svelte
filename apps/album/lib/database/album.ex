defmodule Album do
  @moduledoc false
  use Ecto.Schema

  @primary_key {:id, :binary_id, autogenerate: true}
  schema "albums" do
    field :spotify_id, :string
    field :album_type, :string
    field :artists, {:array, :map}
    field :available_markets, {:array, :string}
    field :external_urls, :map
    field :href, :string
    field :images, {:array, :map}
    field :name, :string
    field :popularity, :integer
    field :release_date, :string
    field :release_date_precision, :string
    field :tracks, :map
    field :type, :string
  end


end
