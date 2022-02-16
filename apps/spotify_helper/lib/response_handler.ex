defmodule SpotifyHelper.ResponseHandler do
  @moduledoc """
  Documentation for `ResponseHandler`.
  """

  def normalize_response({:ok, %{"error" => %{"status" => 401}}}, creds = %Spotify.Credentials{}) do
    # Users already depends on SpotifyHelper, this only works because SpotifyHelper does not
    # explicitly has Users as a dependency.
    new_creds = Users.Token.refresh(creds)
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
