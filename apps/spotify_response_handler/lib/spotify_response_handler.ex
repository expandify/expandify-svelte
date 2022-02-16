defmodule SpotifyResponseHandler do
  @moduledoc """
  Documentation for `SpotifyResponseHandler`.
  """

  def normalize_response({:ok, %{"error" => %{"status" => 401}}}, creds = %Spotify.Credentials{}) do
    new_creds = Exportify.Authenticator.refresh(creds)
    {:retry_with, new_creds}
  end
  def normalize_response({_, %{"meta" => %{"retry_after" => retry_after}}}, creds = %Spotify.Credentials{}) do
    Process.sleep(retry_after * 1000)
    {:retry_with, creds}
  end
  def normalize_response(:ok, _), do: {:empty, ""}
  def normalize_response({:ok, response}, _), do: {:ok, response}
  def normalize_response({:error, reason}, _), do: raise(reason)


end
