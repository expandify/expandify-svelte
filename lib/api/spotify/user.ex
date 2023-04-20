defmodule Api.Spotify.User do

  alias Api.Spotify.Client

  @current_user_url "https://api.spotify.com/v1/me"

  def get_current_user(current_user) do
    Client.get()

  end

end
