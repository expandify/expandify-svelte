defmodule ExportifyDatabase.Saver.Library do
alias ExportifyDatabase.Models.Library, as: ModelLibrary
alias ExportifySpotify.RequestHandler.AccessData

  def save_library(user, artists, albums, songs, playlists) do

    access_data =
      %AccessData{
        storage_pid: nil,
        access_token: user.access_token,
        refresh_token: user.refresh_token
      }
    user_model = ExportifyDatabase.Saver.User.save(user)
    artists_model = ExportifyDatabase.Saver.Artist.save_all(artists)
    albums_model = ExportifyDatabase.Saver.Album.save_all(albums, access_data)
    songs_model = ExportifyDatabase.Saver.Song.save_all(songs, access_data)
    playlists_model = ExportifyDatabase.Saver.Playlist.save_all(playlists, access_data)

    creation =
      NaiveDateTime.utc_now()
      |> NaiveDateTime.truncate(:second)

    model =
      %ModelLibrary{
        name: "Library",
        creation: creation,
        songs: songs_model,
        albums: albums_model,
        artists: artists_model,
        playlists: playlists_model,
        user: user_model
      }
      |> ModelLibrary.changeset()
      |> ExportifyDatabase.Repo.insert()

    IO.inspect("FINISHED")
    model
  end
end