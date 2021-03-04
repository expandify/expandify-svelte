defmodule ExportifyAuthenticator do
  alias ExportifyAuthenticator.JasonWebToken, as: JWT

  def authenticate(conn) do
    case extract_token(conn) do
      {:ok, token} -> verify_token(token)
      error -> error
    end
  end

  defp extract_token(conn) do
    case Plug.Conn.get_req_header(conn, "authorization") do
      [auth_header] -> get_token_from_header(auth_header)
      _ -> {:error, :missing_auth_header}
    end
  end

  defp get_token_from_header(auth_header) do
    {:ok, reg} = Regex.compile("Bearer\:?\s+(.*)$", "i")

    case Regex.run(reg, auth_header) do
      [_, match] -> {:ok, String.trim(match)}
      _ -> {:error, "token not found"}
    end
  end

  def create_token(%{} = data) do
    extra_claims = JWT.create_claims(data)
    {:ok, token, _} = JWT.generate_and_sign(extra_claims)
    token
  end

  defp verify_token(token) do
    JWT.verify_and_validate(token)
  end
end
