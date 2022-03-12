defmodule Artist.Query do
  @moduledoc false

  def find(spotify_id) do
    Artist.Repo.get_by(Artist, spotify_id: spotify_id)
  end

  def save(artist_data = %Artist{}) do
    artist_data
    |> upsert()
  end

  defp upsert(artist_data = %Artist{}) do
    selectors = [spotify_id: artist_data.spotify_id]
    old_artist = Artist.Repo.get_by(Artist, selectors)
    case old_artist do
      nil -> Artist.Repo.insert!(artist_data)
      artist -> artist
    end
  end

  def save_all([%Artist{} | _] = artists) do
    Enum.map(artists, &(save(&1)))
  end

  def convert(artist = %Spotify.Artist{}) do
    %Artist{
      external_urls: artist.external_urls,
      followers: artist.followers,
      genres: artist.genres,
      href: artist.href,
      spotify_id: artist.id,
      images: artist.images,
      name: artist.name,
      popularity: artist.popularity,
      type: artist.type,
      uri: artist.uri,
    }
  end
end
