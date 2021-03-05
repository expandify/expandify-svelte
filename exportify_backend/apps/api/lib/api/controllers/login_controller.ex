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
  @client_id Application.get_env(:exportify_web, ExportifyWeb.Endpoint)[:spotify_client_id]
  @scopes "playlist-read-private playlist-read-collaborative user-library-read user-follow-read user-read-private"
  @client_secret Application.get_env(:exportify_web, ExportifyWeb.Endpoint)[:spotify_client_secret]

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
    IO.inspect(conn)
    json(conn, %{"success" => inspect(conn)})
  end

  @doc """
    Callback function used after a successful spotify login callback.
    Makes a spotify request to get a session token.
    Uses the spotify token to create a user session and read the library.

    Redirects the user to the library view index page.
  """
  def callback(conn, %{"code" => code}) do
    response =
      code
      |> post_body()
      |> token_request()
      |> handle_response

    token = Authenticator.JasonWebToken.create(response)

    conn
    |> put_status(response.status_code)
    |> json(token)
  end

  def callback(conn, params) do
    conn
    |> put_status(401)
    |> json(%{error: params["error"]})
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
    {:ok, response} = Poison.decode(body)

    %{status_code: 200, body: response}
  end

  @doc false
  defp handle_response({:ok, %HTTPoison.Response{status_code: status_code} = response}) do
    %{status_code: status_code, body: response}
  end

  @doc false
  defp handle_response({:error, %HTTPoison.Error{reason: reason}}) do
    %{status_code: 400, response: reason}
  end

  @doc false
  defp handle_response({:ok, response}) do
    %{status_code: 400, body: response}
  end




end
