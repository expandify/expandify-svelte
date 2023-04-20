defmodule ApiWeb.Authentication do
  import Plug.Conn

  @salt "user auth"

  def init(opts), do: opts

  def call(conn, _opts) do
    conn
    |> get_req_header("authorization")
    |> Api.TokenService.get_bearer
    |> Api.TokenService.verify_token
    |> Api.TokenService.get_user
    |> return(conn)
  end

  defp return({:ok, user}, conn), do: assign(conn, :current_user, user)
  defp return({:error, reason}, conn) do
    conn
    |> put_status(401)
    |> Phoenix.Controller.json(reason)
    |> halt
  end


end
