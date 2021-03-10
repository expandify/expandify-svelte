defmodule UserDatabase.Models.User do
  use DatabaseConnector.Schema

  schema "users" do
    field :name, :string
    field :access_token, :string
    field :refresh_token, :string
    field :spotify_id, :string
  end

  def changeset(user, params \\ %{}) do

    user
    |> Ecto.Changeset.cast(params, [:name, :access_token, :refresh_token, :spotify_id])
    |> Ecto.Changeset.validate_required([:access_token, :refresh_token, :spotify_id])
    |> Ecto.Changeset.unique_constraint([:spotify_id])
  end
end
