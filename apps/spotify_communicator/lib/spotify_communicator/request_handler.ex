defmodule SpotifyCommunicator.RequestHandler do

  alias SpotifyCommunicator.{Paging, Client}
  def get_all(spotify_request_func, access_token) do
    spotify_request_func
    |> invoke_spotify_request(access_token)
    |> unpagify(access_token)
    |> create_result
  end

  defp invoke_spotify_request(spotify_request, access_token) do
    case spotify_request.() do
      {:ok, response} -> response
      {:retry, _} = retry -> retry_after(retry, spotify_request, access_token)
      {:access_token_expired, _} = ate -> ate
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

    get_all(spotify_request_func, access_token)

  end

  defp unpagify(%Paging{next: "null"} = obj, _), do: obj.items
  defp unpagify(%Paging{} = obj, access_token) do
    next_items = get_all(fn -> Client.get(access_token, obj.next) end, access_token)

    obj.items ++ next_items
  end
  defp unpagify(obj, _) when is_list(obj), do: obj
  defp unpagify({:error, _} = err, _), do: err
  defp unpagify(obj, _), do: [obj]

  defp create_result(obj) when is_list(obj), do: {:ok, obj}
  defp create_result({:error, _} = err), do: err

end


##TODO what happens when access token gets denied
