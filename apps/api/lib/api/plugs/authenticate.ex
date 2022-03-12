defmodule Api.Plugs.Authenticate do
  import Plug.Conn


  def init(opts), do: opts

  def call(conn, _opts) do
    case authenticate(conn) do
      {:ok, user} -> success(conn, user)
      {:error, reason} -> forbidden(conn, reason)
    end
  end

  defp authenticate(conn) do
    case extract_token(conn) do
      {:ok, token} ->
        Phoenix.Token.verify(conn, "user auth", token, max_age: 86400)
        |> get_user()
      {:error, error} ->
        {:error, error}
    end
  end

  defp extract_token(conn) do
    case Plug.Conn.get_req_header(conn, "authorization") do
      [auth_header] -> get_token_from_header(auth_header)
      _ -> {:error, "missing_auth_header"}
    end
  end

  defp get_token_from_header(auth_header) do
    {:ok, reg} = Regex.compile("Bearer\:?\s+(.*)$", "i")

    case Regex.run(reg, auth_header) do
      [_, match] -> {:ok, String.trim(match)}
      _ -> {:error, "token not found"}
    end
  end

  defp get_user({:error, reason}), do: {:error, reason}
  defp get_user({:ok, user_id}) do
    case User.Query.find(user_id) do
      nil -> {:error, "No Credentials for the user were found"}
      user -> {:ok, user}
    end
  end

  defp success(conn, user) do
    credentials = %Spotify.Credentials{
      access_token: user.access_token,
      refresh_token: user.refresh_token
    }
    assign(conn, :spotify_credentials, credentials)
  end

  defp forbidden(conn, reason) do
    conn
    |> put_status(401)
    |> Phoenix.Controller.json(%{error: reason})
    |> halt
  end

end
