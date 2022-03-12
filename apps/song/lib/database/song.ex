defmodule Song do
  @moduledoc false
  use Ecto.Schema

  @primary_key {:id, :binary_id, autogenerate: true}
  schema "songs" do
    field :spotify_id, :string
    field :album, :map
    field :artists, {:array, :map}
    field :available_markets, {:array, :string}
    field :disc_number, :integer
    field :duration_ms, :integer
    field :explicit, :boolean
    field :external_ids, :map
    field :href, :string
    field :is_playable, :boolean
    field :linked_from, :map
    field :name, :string
    field :popularity, :integer
    field :preview_url, :string
    field :track_number, :integer
    field :type, :string
    field :uri, :string
  end

end
