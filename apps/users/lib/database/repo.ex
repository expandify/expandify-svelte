defmodule Users.Repo do
  @moduledoc false
  use Ecto.Repo,
      otp_app: :users,
      adapter: Mongo.Ecto
end