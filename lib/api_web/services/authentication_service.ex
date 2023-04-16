defmodule ApiWeb.AuthenticationService do
  import Plug.Conn

  @salt "user auth"

  def init(opts), do: opts

  def call(conn, _opts) do
    conn
    |> get_req_header("authorization")
    |> get_bearer
    |> verify_token
    |> get_user
    |> return(conn)
  end

  def sign_token(data) do
    Phoenix.Token.sign(ApiWeb.Endpoint, @salt, data)
  end

  defp get_bearer(["bearer " <> token]), do: {:ok, token}
  defp get_bearer(["Bearer " <> token]), do: {:ok, token}
  defp get_bearer(_), do: {:error, "token not found"}


  defp verify_token({:error, _} = err), do: err
  defp verify_token({:ok, token}), do: Phoenix.Token.verify(ApiWeb.Endpoint, @salt, token)

  defp get_user({:error, reason}), do: {:error, reason}
  defp get_user({:ok, user_id}) do
    # case User.Query.find(user_id) do
    #   nil -> {:error, "No Credentials for the user were found"}
    #   user -> {:ok, user}
    # end
    {:ok, "#{user_id}"}
  end

  defp return({:ok, user}, conn), do: assign(conn, :current_user, user)
  defp return({:error, reason}, conn) do
    conn
    |> put_status(401)
    |> Phoenix.Controller.json(reason)
    |> halt
  end


end
