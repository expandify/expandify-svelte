defmodule Api.Spotify.Client do


  def get(url, current_user, headers \\ []),                do: request(:get, url, current_user, "", headers)
  def put(url, current_user, body \\ "", headers \\ []),    do: request(:put, url, current_user, body, headers)
  def post(url, current_user, body \\ "", headers \\ []),   do: request(:post, url, current_user, body, headers)
  def delete(url, current_user, headers \\ []),             do: request(:delete, url, current_user, "", headers)


  defp request(type, url, current_user, body, headers) do
    request_func = fn(current_user) -> HTTPoison.request(type, url, body, create_headers(current_user, headers)) end
    execute_call(request_func, current_user)
  end

  defp execute_call(request_func, current_user) do
    request_func.(current_user)
      |> decode_response()
      |> handle_response(current_user, request_func)
  end

  defp create_headers(nil, headers),            do: headers
  defp create_headers(current_user, headers),   do: [{"Authorization", "Bearer #{current_user.access_token}"} | create_headers(nil, headers)]

  defp handle_response({:ok, response}, _, _),                              do: {:ok, response}
  defp handle_response({:error, reason}, _,_),                              do: {:error, reason}
  defp handle_response({:retry, retry_after}, current_user, request_func),  do: retry_after(request_func, retry_after, current_user)
  defp handle_response({:unauthorized, _}, current_user, request_func),     do: refresh_and_retry(request_func, current_user)

  defp decode_response({:ok, %HTTPoison.Response{status_code: code, body: body}}) when code in 200..299,  do: {:ok, Jason.decode(body)}
  defp decode_response({:ok, %HTTPoison.Response{status_code: code, body: body}}) when code == 401,       do: {:unauthorized, Jason.decode(body)}
  defp decode_response({:ok, %HTTPoison.Response{status_code: code, headers: headers}}) when code == 429, do: {:retry, get_rety_after(headers)}
  defp decode_response({:ok, response}),                                                                  do: {:error, %{status_code: response.status_code, reason: response.reason}} # Happens on any other status code
  defp decode_response({:error, %HTTPoison.Error{reason: reason}}),                                       do: {:error, reason} # HTTPoison error

  defp get_rety_after(headers) do
    {retry_after, ""} =
      headers
      |> Enum.find(&(Kernel.elem(&1, 0) == "retry-after"))
      |> Kernel.elem(1)
      |> Integer.parse()
    retry_after
  end

  defp retry_after(func, time, current_user) do
    # TODO this may cause an infinite loop
    Process.sleep((time * 1000))
    execute_call(func, current_user)
  end

  defp refresh_and_retry(func, current_user) do
    result = Api.AuthenticationService.refresh(current_user)

    case result do
       {:ok, refreshed} -> execute_call(func, refreshed)
       {:error, reason} -> {:error, reason}
    end
  end
end
