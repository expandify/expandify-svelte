defmodule ExternalUrls do
  use Ecto.Schema

  embedded_schema do
    field :spotify, :string
  end

end
