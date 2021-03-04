defmodule ExportifySpotify.RequestHandler do
  @moduledoc """
      Handles spotify request and decodes the responses.
  """

  defmodule SpotifyRequest do
    @moduledoc """
      Provides a struct that represents a spotify request.
      Contains the request url and its parameters.
    """
    defstruct [:url, :params]
  end

  defmodule AccessData do
    @moduledoc """
      Provides a struct that represents the specific user data.
      Contains an access_token and the storage_pid used to safe the spotify data in.
    """
    defstruct [:access_token, :storage_pid, :refresh_token, :expires_in]
  end

  @doc """
      Makes a spotify request with the given request struct and user data.
      Decodes the response and raises an error on an unsuccessful request.
      If the spotify data limit is reached this function will wait until it can retry the request.
  """
  def spotify_request(%SpotifyRequest{} = request, %AccessData{} = user) do
    headers = [Authorization: "Bearer #{user.access_token}"]

    {status, response} =
      HTTPoison.get(request.url, headers, params: request.params)
      |> decode_response()

    case status do
      :ok -> response
      :retry -> retry_request_after(request, user, response["retry_after"])
      :error -> raise("Something Wrong With the request")
    end
  end

  @doc false
  defp decode_response({:ok, %HTTPoison.Response{status_code: code, body: body}}) when code in 200..299 do
    {:ok, Poison.decode(body)}
  end

  @doc false
  defp decode_response({:ok, %HTTPoison.Response{status_code: code, headers: headers}}) when code == 429 do
    {retry_after, ""} =
      headers
      |> Enum.find(&(Kernel.elem(&1, 0) == "retry-after"))
      |> Kernel.elem(1)
      |> Integer.parse()

    {:retry, %{"retry_after" => retry_after}}
  end

  @doc false
  defp decode_response({:ok, %HTTPoison.Response{status_code: code} = response}) when code in 400..499 do
    {:error, response}
  end


  @doc false
  defp decode_response({:error, %HTTPoison.Error{reason: :timeout}}) do
    {:retry, %{"retry_after" => 1}}
  end

  @doc false
  defp decode_response({:ok, %HTTPoison.Response{status_code: code}}) when code == 504 do
    {:retry, %{"retry_after" => 1}}
  end


  @doc false
  defp decode_response({:error, reason}) do
    # happens on HTTPoison error
    {:error, reason}
  end

  @doc false
  defp retry_request_after(%SpotifyRequest{} = request, %AccessData{} = user, retry_after) do
    # TODO this can result in an endless loop.
    # needs some way to check, weather or not this is the first retry
    Process.sleep((retry_after * 1000))
    spotify_request(request, user)
  end

  @doc """
    Handles the response of a spotify request.
  """
  def handle_response({:ok, response}), do: response
  def handle_response(_), do: raise("Something wrong with the request.")
end
