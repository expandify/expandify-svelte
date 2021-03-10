defmodule SpotifyCommunicator.Paging do
  
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
