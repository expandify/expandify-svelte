defmodule Playlist.Repo do
  @moduledoc false
  use Ecto.Repo,
      otp_app: :playlist,
      adapter: Mongo.Ecto
end
