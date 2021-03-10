defmodule UserDatabase.Repo do
  use DatabaseConnector.Query
  use Ecto.Repo,
    otp_app: :user_database,
    adapter: Ecto.Adapters.MyXQL

  def update_access_token(user_spotify_id, new_access_token) do

    case get_by_spotify_id(UserDatabase.Models.User, user_spotify_id) do
      {:ok, user} -> Ecto.Changeset.change(user, %{access_token: new_access_token}) |> Repo.update()
      {:error, _} = err -> err
    end
  end
end
