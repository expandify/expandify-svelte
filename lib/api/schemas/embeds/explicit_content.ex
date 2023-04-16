defmodule ExplicitContent do
  use Ecto.Schema

  embedded_schema do
    field :filter_enabled, :boolean
    field :filter_locked, :boolean
  end
end
