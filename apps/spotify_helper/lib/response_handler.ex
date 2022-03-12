defmodule SpotifyHelper.ResponseHandler do
  @moduledoc """
  Documentation for `ResponseHandler`.
  """

  def normalize_response({:ok, %{"error" => %{"status" => 401}}}), do: :token_expired
  def normalize_response({_, %{"meta" => %{"retry_after" => retry_after}}}) do
    Process.sleep(retry_after * 1000)
    :retry
  end
  def normalize_response(:ok), do: :empty
  def normalize_response({:ok, response}), do: response
  def normalize_response({:error, reason}), do: raise(reason)


end
