defmodule Artist.Repo do
  @moduledoc false
  use Ecto.Repo,
      otp_app: :artist,
      adapter: Mongo.Ecto
end
