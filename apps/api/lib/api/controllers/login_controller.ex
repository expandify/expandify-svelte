defmodule API.LoginController do
  @moduledoc """
    This module is responsible for the coordination of the spotify login.
  """
  use API, :controller

  @login_url "https://accounts.spotify.com/authorize"
  @response_type "code"
  @show_dialog "true"
  @grant_type "authorization_code"
  @token_url "https://accounts.spotify.com/api/token"
  @client_id Application.get_env(:api, API.Endpoint)[:spotify_client_id]
  @scopes "playlist-read-private playlist-read-collaborative user-library-read user-follow-read user-read-private"
  @client_secret Application.get_env(:api, API.Endpoint)[:spotify_client_secret]

  def login(conn, _params) do
    response =
      @login_url
      |> URI.parse()
      |> Map.put(
        :query,
        URI.encode_query(%{
          client_id: @client_id,
          scope: @scopes,
          response_type: @response_type,
          redirect_uri: API.Router.Helpers.login_url(API.Endpoint, :callback),
          show_dialog: @show_dialog
        })
      )
      |> URI.to_string()

    json(conn, %{login_url: response})
  end

  def auth(conn, _params) do
    json(conn, %{"success" => conn.assigns.claims["access_token"]})
  end

  @doc """
    Callback function used after a successful spotify login callback.
    Makes a spotify request to get a session token.
    Uses the spotify token to create a user session and read the library.

    Redirects the user to the library view index page.
  """
  def callback(conn, %{"code" => code}) do
    code
    |> post_body
    |> token_request
    |> handle_response
    |> upsert_user_data
    |> build_jwt
    |> build_response(conn)
  end

  def callback(conn, params) do
    conn
    |> put_status(401)
    |> json(%{error: params["error"]})
  end

  def upsert_user_data({status_code, response}) when status_code != 200, do: {:error, response}

  def upsert_user_data({status_code, response}) when status_code == 200 do
    access_token = Map.get(response, "access_token")
    refresh_token = Map.get(response, "refresh_token")

    # retrieve user data from spotify
    # build a User object (maybe this happens while retrieving already)
    # safe to database
    # return {:ok, user_obj} on success, {:error, reason} on failure

    {:ok, %{access_token: access_token, refresh_token: refresh_token}}
  end

  def build_jwt({:ok, user}), do: Authenticator.JasonWebToken.create(user)
  def build_jwt({_, _}), do: {:error, "token could not be created"}

  def build_response({:ok, token}, conn) do
    conn
    |> put_status(200)
    |> json(%{token: token})
  end

  def build_response({_, reason}, conn) do
    conn
    |> put_status(401)
    |> json(%{error: reason})
    |> halt
  end

  @doc false
  defp post_body(code) do
    %{
      grant_type: @grant_type,
      code: code,
      redirect_uri: API.Router.Helpers.login_url(API.Endpoint, :callback),
      client_id: @client_id,
      client_secret: @client_secret
    }
    |> URI.encode_query()
  end

  @doc false
  defp token_request(post_params) do
    HTTPoison.post(
      @token_url,
      post_params,
      [
        {"Content-Type", "application/x-www-form-urlencoded"}
      ]
    )
  end

  @doc false
  defp handle_response({:ok, %HTTPoison.Response{status_code: 200, body: body}}) do
    {:ok, response} = Jason.decode(body)

    {200, response}
  end

  @doc false
  defp handle_response({:ok, %HTTPoison.Response{status_code: status_code} = response}) do
    {status_code, response}
  end

  @doc false
  defp handle_response({:error, %HTTPoison.Error{reason: reason}}) do
    {400, reason}
  end

  @doc false
  defp handle_response({:ok, response}) do
    {400, response}
  end
end
