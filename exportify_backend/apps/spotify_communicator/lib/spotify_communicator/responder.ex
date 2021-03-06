defmodule SpotifyCommunicator.Responder do

  @callback build_response(map) :: any

  defmacro __using__(_) do
    quote do
      def handle_response({:ok, %HTTPoison.Response{status_code: code, body: ""}})
          when code in 200..299,
          do: :complete

      def handle_response({message, %HTTPoison.Response{status_code: 429, body: body, headers: headers}}) do
        {retry_after, ""} =
          headers
          |> Enum.find(fn {key, value} -> String.downcase(key) == "retry-after" end)
          |> Kernel.elem(1)
          |> Integer.parse()

        {:retry, Map.put(Jason.decode!(body), "meta", %{"retry_after" => retry_after})}
      end

      def handle_response({message, %HTTPoison.Response{status_code: code, body: body}})
          when code in 400..499 do
        {:error, Jason.decode!(body)}
      end

      def handle_response({:ok, %HTTPoison.Response{status_code: _code, body: body}}) do
        response = body |> Jason.decode!() |> build_response

        {:ok, response}
      end

      def handle_response({:error, %HTTPoison.Error{reason: reason}}) do
        {:error, reason}
      end
    end
  end
end
