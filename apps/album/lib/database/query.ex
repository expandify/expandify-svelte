defmodule Album.Query do
  @moduledoc false

  def find(spotify_id) do
    Album.Repo.get_by(Album, spotify_id: spotify_id)
  end

  def save(album_data = %Album{}) do
    album_data
    |> upsert()
  end

  defp upsert(album_data = %Album{}) do
    selectors = [spotify_id: album_data.spotify_id]
    old_album = Album.Repo.get_by(Album, selectors)
    case old_album do
      nil -> Album.Repo.insert!(album_data)
      album -> album
    end
  end

  def save_all([%Album{} | _] = albums) do
    Enum.map(albums, &(save(&1)))
  end

  def convert(album = %Spotify.Album{}) do
    %Album{
      album_type: album.album_type,
      artists: album.artists,
      available_markets: album.available_markets,
      external_urls: album.external_urls,
      href: album.href,
      spotify_id: album.id,
      images: album.images,
      name: album.name,
      popularity: album.popularity,
      release_date: album.release_date,
      release_date_precision: album.release_date_precision,
      tracks: album.tracks,
      type: album.type,
    }
  end
end
