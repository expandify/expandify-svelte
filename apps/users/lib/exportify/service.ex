defmodule Users.Service do
  @moduledoc false

  def find(spotify_id) do
    Users.Repo.get_by(User, spotify_id: spotify_id)
  end

  def upsert(user_data = %User{}) do
    case find(user_data.spotify_id) do
      nil -> Users.Repo.insert(user_data)
      user -> Ecto.Changeset.change(user, access_token: user_data.access_token, refresh_token: user_data.refresh_token ) |> Users.Repo.update()
    end
  end
end
