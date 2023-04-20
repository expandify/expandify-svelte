defmodule Api.LoginService do

  def login(access_token, refresh_token) do
    user = %User{access_token: access_token, refresh_token: refresh_token}
    user
    |> Spotify.get(:user)
    
  end
end
