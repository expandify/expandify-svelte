defmodule SpotifyCommunicator.Paging do

  @derive Jason.Encoder
  defstruct ~w[
    href
    items
    limit
    next
    offset
    previous
    total
    cursors
  ]a

end
