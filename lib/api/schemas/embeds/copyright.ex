defmodule Copyright do
  use Ecto.Schema

  embedded_schema do
    field :text, :string
    field :type, :string
  end

end
