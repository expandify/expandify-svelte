defmodule Image do
  use Ecto.Schema

  embedded_schema do
    field :url, :string
    field :height, :integer
    field :width, :integer
  end

end
