defmodule Restrictions do
  use Ecto.Schema

  embedded_schema do
    field :reason, :string
  end

end
