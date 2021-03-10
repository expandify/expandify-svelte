defmodule ExportifyLocalStorage.Item do
  @moduledoc """
    This module provides functions to simplify the process of retrieving spotify data.
  """

  alias ExportifySpotify.RequestHandler.SpotifyRequest
  alias ExportifySpotify.RequestHandler.AccessData

  @doc """
    Backs up all items the can be retrieved from the specified url.
    For each response the given "process_func" will be called.
  """
  def backup_from_link(url, %AccessData{} = user, process_func, params \\ %{}) do
    response =
      %SpotifyRequest{url: url, params: params}
      |> ExportifySpotify.RequestHandler.spotify_request(user)
      |> ExportifySpotify.RequestHandler.handle_response()

    {:ok, items, next} = process_func.(response, user)

    if next != nil, do: items ++ backup_from_link(next, user, process_func, params), else: items
  end


end
