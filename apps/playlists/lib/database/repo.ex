defmodule Playlists.Repo do
  @moduledoc false
  use Ecto.Repo,
      otp_app: :playlists,
      adapter: Mongo.Ecto
end
