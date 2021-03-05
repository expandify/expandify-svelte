defmodule API.Plugs.Authenticate do
  import Plug.Conn
  alias ExportifyAuthenticator.JasonWebToken, as: JWT


  def init(opts), do: opts

  def call(conn, _opts) do
    case authenticate(conn) do
      {:ok, token} -> success(conn, token)
      { :error, reason } -> forbidden(conn, reason)
    end
  end

  defp success(conn, token) do
    assign(conn, :claims, token["claims"])
  end

  defp forbidden(conn, reason) do
    conn
    |> put_status(401)
    |> Phoenix.Controller.json(%{error: reason})
    |> halt
  end

  def authenticate(conn) do
    case extract_token(conn) do
      {:ok, token} -> JWT.verify(token)
      error -> error
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


end
