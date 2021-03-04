defmodule ExportifyWeb.LoginController do
  @moduledoc """
    This module is responsible for the coordination of the spotify login.
  """
  use ExportifyWeb, :controller

  alias ExportifyLocalStorage.Library

  @login_url "https://accounts.spotify.com/authorize"
  @response_type "code"
  @show_dialog "true"
  @grant_type "authorization_code"
  @token_url "https://accounts.spotify.com/api/token"
  @client_id Application.get_env(:exportify_web, ExportifyWeb.Endpoint)[:spotify_client_id]
  @scopes "playlist-read-private playlist-read-collaborative user-library-read user-follow-read user-read-private"
  @client_secret Application.get_env(:exportify_web, ExportifyWeb.Endpoint)[:spotify_client_secret]


  @doc """
    Callback function used after a successful spotify login callback.
    Makes a spotify request to get a session token.
    Uses the spotify token to create a user session and read the library.

    Redirects the user to the library view index page.
  """
  def callback(conn, %{"code" => code}) do
    {:ok, response} =
      code
      |> post_body()
      |> token_request()
      |> handle_response

    conn = create_user_session(conn, response)

    conn
    |> redirect(to: ExportifyWeb.Router.Helpers.library_path(ExportifyWeb.Endpoint, :index))
  end

  def callback(conn, _params) do
    json(conn, %{error: "no parameters"})
  end

  @doc false
  defp post_body(code) do
    %{
      grant_type: @grant_type,
      code: code,
      redirect_uri: ExportifyWeb.Router.Helpers.login_url(ExportifyWeb.Endpoint, :callback),
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

    {:ok, response}
  end

  @doc false
  defp handle_response({:ok, %HTTPoison.Response{} = response}) do
    %{response: response}
  end

  @doc false
  defp handle_response({:error, %HTTPoison.Error{reason: reason}}) do
    %{error: "post error", reason: reason}
  end

  @doc false
  defp create_user_session(conn, response) do
    token = %{
      access_token: response["access_token"],
      expires_in: response["expires_in"],
      refresh_token: response["refresh_token"]
    }

    old_session = Plug.Conn.get_session(conn)

    conn
    |> configure_session(old_session, token)
    |> Plug.Conn.configure_session(renew: true)
  end

  @doc false
  defp configure_session(conn, _session, token) do
    # TODO Session is only unused to clear it. This makes testing easy.
    session = %{}

    if not session_valid?(session) do
      conn
      |> Plug.Conn.clear_session()
      |> Plug.Conn.put_session(:library_pids, Library.start_download(token))
    else
      conn
    end
  end

  @doc """
    Checks weather the current session is still valid.
    Validity is defined by the status of the storage processes.
    A valid session means, that the storage is still available and can be retrieved.
    An invalid session means, that at least one of the storage processes is dead and
    not the library has to be retrieved again.
  """
  def session_valid?(session) do
    library_pids = session["library_pids"]

    case library_pids != nil and Process.alive?(library_pids) do
      true ->
        ExportifyLocalStorage.LibraryStorage.get_items(library_pids)
        |> library_alive?

      false ->
        false
    end
  end

  @doc """
    Checks weather or not the libraries given are alive.
    All libraries will be checked by `library_content_alive?/1`.
    If any of the libraries is not alive, this will return false.
  """
  def library_alive?([]), do: false

  def library_alive?(libraries) do

    libraries
    |> Enum.map(&library_content_alive?(&1))
    |> Enum.reduce(fn x, acc -> x and acc end)
  end

  @doc """
    Checks weather or not the given Library is alive.
    If any of the attached process ids are not alive, this will return false.
  """
  def library_content_alive?(library) do


    playlists_pid = library.playlists_pid
    songs_pid = library.songs_pid
    artists_pid = library.artists_pid
    albums_pid = library.albums_pid

    playlists_pid != nil and Process.alive?(playlists_pid) and
      (songs_pid != nil and Process.alive?(songs_pid)) and
      (artists_pid != nil and Process.alive?(artists_pid)) and
      (albums_pid != nil and Process.alive?(albums_pid))
  end

  @doc """
    Creates the spotify login url.
    The spotify url includes:
      - Client Id
      - scopes
      - redirect uri (set to the :callback Endpoint of this App)
    This url will always show the spotify login prompt to the caller.
  """
  def spotify_login_url() do
    @login_url
    |> URI.parse()
    |> Map.put(
      :query,
      URI.encode_query(%{
        client_id: @client_id,
        scope: @scopes,
        response_type: @response_type,
        redirect_uri: ExportifyWeb.Router.Helpers.login_url(ExportifyWeb.Endpoint, :callback),
        show_dialog: @show_dialog
      })
    )
    |> URI.to_string()
  end
end
