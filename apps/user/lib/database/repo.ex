defmodule User.Repo do
  @moduledoc false
  use Ecto.Repo,
      otp_app: :user,
      adapter: Mongo.Ecto
end