defmodule Playlist do
  @moduledoc false
  use Ecto.Schema

  @primary_key {:id, :binary_id, autogenerate: true}
  schema "playlist" do
    field :spotify_id, :string
    field :collaborative, :boolean
    field :description, :string
    field :external_urls, :map
    field :followers, :map
    field :href, {:array, :map}
    field :images, :string
    field :name, :string
    field :owner, :map
    field :public, :boolean
    field :snapshot_id, :string
    field :tracks, :map
    field :type, :string
    field :uri, :string
  end
end
