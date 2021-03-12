defmodule SpotifyCommunicator.RequestHandler do

  defp invoke_spotify_request(spotify_request, access_token) do
    case spotify_request.() do
      {:ok, response} -> response
      {:retry, _} = retry -> retry_after(retry, spotify_request, access_token)
      {:access_token_expired, _} = access_token_expired -> access_token_expired
      :complete -> :complete
      {:error, _} = err -> err
      _ -> {:error, "unexpected return from spotify api call"}
    end
  end

  def retry_after({:retry, response}, spotify_request_func, access_token) do
    # TODO this can result in an endless loop.
    # needs some way to check, weather or not this is the first retry
    Map.get(response, "meta", %{})
    |> Map.get("retry_after", 1)
    |> Kernel.*(1000)
    |> Process.sleep

    invoke_spotify_request(spotify_request_func, access_token)
  end

end
