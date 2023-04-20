defmodule Api.AuthenticationService do

  @login_url "https://accounts.spotify.com/authorize"
  @response_type "code"
  @show_dialog "true"
  @token_url "https://accounts.spotify.com/api/token"
  @client_id Application.compile_env(:api, API.Endpoint)[:spotify_client_id]
  @scopes "playlist-read-private playlist-read-collaborative user-library-read user-follow-read user-read-private"
  @client_secret Application.compile_env(:api, API.Endpoint)[:spotify_client_secret]

  def login_url do
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
        }))
    |> URI.to_string()
  end

  def tokens(code) do
    code
    |> token_body
    |> token_request
    |> parse_response
    |> Api.TokenService.sign_token
  end

  def refresh(current_user) do
    current_user.refresh_token
    |> refresh_body
    |> token_request
    |> parse_response

    # Api.Repo.insert(SpotifyUser, user)
  end

  defp token_body(code) do
    %{
      grant_type: "authorization_code",
      code: code,
      redirect_uri: API.Router.Helpers.login_url(API.Endpoint, :callback),
    }
    |> URI.encode_query()
  end

  defp refresh_body(refresh_token) do
    %{
      grant_type: "refresh_token",
      refresh_token: refresh_token,
    }
    |> URI.encode_query()
  end

  defp token_request(post_params) do
    Api.Spotify.Client.post(
      @token_url,
      nil,
      post_params,
      [
        {"Content-Type", "application/x-www-form-urlencoded"},
        {"Authorization", "Basic #{Base.encode64("#{@client_id}:#{@client_secret}")}"}
      ]
    )
  end

  defp parse_response({:error, _} = err), do: err
  defp parse_response({:ok, response}) do
    access_token = Map.get(response, "access_token")
    refresh_token = Map.get(response, "refresh_token")

    {:ok, %{access_token: access_token, refresh_token: refresh_token}}
  end

end
