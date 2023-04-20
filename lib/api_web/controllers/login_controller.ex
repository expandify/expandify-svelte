defmodule ApiWeb.LoginController do
  use ApiWeb, :controller

  def login(conn, %{"access_token" => access_token, "refresh_token" => refresh_token}) do
    token = ApiWeb.TokenService.sign_token(%{"access_token" => access_token, "refresh_token" => refresh_token})
    json(conn, %{token: token})
  end
  def login(conn, %{"refresh_token" => _}), do: conn |> put_status(:unauthorized) |> json(%{error: "No access_token found."})
  def login(conn, %{"access_token" => _}), do: conn |> put_status(:unauthorized) |> json(%{error: "No refresh_token found."})
  def login(conn, _), do: conn |> put_status(:unauthorized) |> json(%{error: "No access_token and refresh_token found."})
end
