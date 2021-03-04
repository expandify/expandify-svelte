defmodule ExportifyDatabase.Saver.User do

  alias ExportifyDatabase.Models.User, as: ModelUser
  def save(user) do

    on_conflict = %{access_token: user.access_token, refresh_token: user.refresh_token}


    {:ok, model} =
      %ModelUser{
        name: user.name,
        spotify_id: user.spotify_id,
        access_token: user.access_token,
        refresh_token: user.refresh_token
      }
      |> ExportifyDatabase.Util.Helpers.upsert(on_conflict)
    model
  end

end