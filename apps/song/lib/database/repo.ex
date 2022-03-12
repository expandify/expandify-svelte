defmodule Song.Repo do
  @moduledoc false
  use Ecto.Repo,
      otp_app: :song,
      adapter: Mongo.Ecto
end
