defmodule ExternalIds do
  use Ecto.Schema

  embedded_schema do
    field :isrc, :string
    field :ean, :string
    field :upc, :string
  end

end
