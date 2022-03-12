defmodule Album.Repo do
  @moduledoc false
  use Ecto.Repo,
      otp_app: :album,
      adapter: Mongo.Ecto
end
