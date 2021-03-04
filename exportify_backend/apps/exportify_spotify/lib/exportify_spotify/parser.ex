
defmodule ExportifySpotify.Artist do
  @moduledoc """
    The internal representation of an Artist.
  """
  defstruct [:name, :image, :spotify_id]

  def get_info(info) do

    artist_images = Map.get(info, :images) || Map.get(info, "images") || Map.get(info, :image) || Map.get(info, "image") || []
    img = if is_list(artist_images), do: Enum.at(artist_images, 0, %{"url" => ""}), else: %{"url" => artist_images}


    %ExportifySpotify.Artist{
      name: Map.get(info, :name) || Map.get(info, "name"),
      image:  Map.get(img, :url) || Map.get(img, "url"),
      spotify_id: Map.get(info, :spotify_id) || Map.get(info, "spotify_id") || Map.get(info, :id) || Map.get(info, "id")
    }
  end
end

defmodule ExportifySpotify.Album do
  @moduledoc """
    The internal representation of an Album.
  """
  defstruct [:name, :image, :artists, :spotify_id]

  def get_info(album) do
    artists =
      Map.get(album, :artists) || Map.get(album, "artists")
      |> Enum.map(&(%ExportifySpotify.Artist{name: Map.get(&1, :name) || Map.get(&1, "name"), spotify_id: Map.get(&1, :id) || Map.get(&1, "id")}))



    album_images = Map.get(album, :images) || Map.get(album, "images") || Map.get(album, :image) || Map.get(album, "image") || []
    img = if is_list(album_images), do: Enum.at(album_images, 0, %{"url" => ""}), else: %{"url" => album_images}

    %ExportifySpotify.Album{
      name: Map.get(album, :name) || Map.get(album, "name"),
      image:  Map.get(img, :url) || Map.get(img, "url"),
      artists: artists,
      spotify_id: Map.get(album, :spotify_id) || Map.get(album, "spotify_id") || Map.get(album, :id) || Map.get(album, "id")
    }
  end
end

defmodule ExportifySpotify.Song do
  @moduledoc """
    The internal representation of an Song.
  """
  defstruct [:name, :duration, :artists, :album, :spotify_id]


  @doc """
      Converts milliseconds in a beatified time format.
  """
  def ms_to_seconds(ms) do
    Time.add(~T[00:00:00], ms, :millisecond)
    |> Time.truncate(:second)
    |> Time.to_string()
  end

  @doc """
     Retrieves all necessary song information and returns them in a map.
  """
  def get_info(spotify_song_info) do
    song = spotify_song_info["track"]

    artists =
      Map.get(song, :artists) || Map.get(song, "artists")
      |> Enum.map(&(%ExportifySpotify.Artist{name: Map.get(&1, :name) || Map.get(&1, "name"), spotify_id: Map.get(&1, :id) || Map.get(&1, "id")}))


    album = Map.get(song, :album) || Map.get(song, "album")
    album_info =
      %ExportifySpotify.Album{name: Map.get(album, :name) || Map.get(album, "name"), spotify_id: Map.get(album, :id) || Map.get(album, "id")}

    %ExportifySpotify.Song{
      name: Map.get(song, :name) || Map.get(song, "name"),
      duration: ms_to_seconds(Map.get(song, :duration_ms) || Map.get(song, "duration_ms")),
      artists: artists,
      album: album_info,
      spotify_id: Map.get(song, :spotify_id) || Map.get(song, "spotify_id") || Map.get(song, :id) || Map.get(song, "id")
    }
  end
end

defmodule ExportifySpotify.Playlist do
  @moduledoc """
    The internal representation of an Playlist.
  """
  defstruct [:name, :song_count, :spotify_id, :songs, :description]



  def get_info(info, songs) do

    tracks = Map.get(info, :tracks) || Map.get(info, "tracks")

    %ExportifySpotify.Playlist{
      name: Map.get(info, :name) || Map.get(info, "name"),
      song_count: Map.get(tracks, :total) || Map.get(tracks, "total"),
      spotify_id: Map.get(info, :spotify_id) || Map.get(info, "spotify_id") || Map.get(info, :id) || Map.get(info, "id"),
      songs: songs,
      description: Map.get(info, :description) || Map.get(info, "description")
    }
  end
end

defmodule ExportifySpotify.User do
  @moduledoc """
    The internal representation of an User.
  """
  defstruct [:name, :spotify_id, :access_token, :refresh_token, :expires_in]

  def get_info(response, user) do
    %ExportifySpotify.User{

      name: Map.get(response, :display_name) || Map.get(response, "display_name"),
      spotify_id:  Map.get(response, :spotify_id) || Map.get(response, "spotify_id") || Map.get(response, :id) || Map.get(response, "id"),
      access_token: Map.get(user, :access_token) || Map.get(user, "access_token"),
      refresh_token: Map.get(user, :refresh_token) || Map.get(user, "refresh_token"),
      expires_in: Map.get(user, :expires_in) || Map.get(user, "expires_in")
    }
  end
end
