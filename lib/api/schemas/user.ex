defmodule User do
  use Api.Schema

  schema "users" do
    field :access_token, :string
    field :refresh_token, :string
    belongs_to :spotify_user, SpotifyUser
  end
end
