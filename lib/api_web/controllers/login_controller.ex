defmodule ApiWeb.LoginController do
  use ApiWeb, :controller

  def login(conn, _) do
    token = ApiWeb.AuthenticationService.sign_token("useId")
    json(conn, %{token: token})
  end
end
