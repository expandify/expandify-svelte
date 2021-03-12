defmodule SpotifyCommunicator.TimedObj do
  @moduledoc """
    This module defines a Track, that may also contain information about the date it was added to a playlist or library.
  """
  import SpotifyCommunicator.Helpers
  alias SpotifyCommunicator.Paging
  defstruct ~w[
    added_at
    added_by
    is_local
    track
  ]a

  defp pack_track(track, key, struct_type) do
    track_info = to_struct(SpotifyCommunicator.TimedObj, track)
    Map.put(track_info, key, to_struct(struct_type, Map.get(track_info, key)))
  end

  def build_paged_timed_obj(body, key, struct_type) do
    %Paging{
      href: body["href"],
      items: body["items"] |> Enum.map(fn track -> pack_track(track, key, struct_type) end),
      limit: body["limit"],
      next: body["next"],
      offset: body["offset"],
      previous: body["previous"],
      total: body["total"]
    }
  end
end
