defmodule ResumePoint do
  use Ecto.Schema

  embedded_schema do
    field :fully_played, :boolean
    field :resume_position_ms, :integer
  end

end
