defmodule User do
  @moduledoc false
  use Ecto.Schema

  @primary_key {:id, :binary_id, autogenerate: true}
  schema "user" do
    field :spotify_id, :string
    field :access_token, :string
    field :refresh_token, :string
  end
end
