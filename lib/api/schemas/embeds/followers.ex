defmodule Followers do
  use Ecto.Schema

  embedded_schema do
    field :href, :string
    field :total, :integer
  end

end
