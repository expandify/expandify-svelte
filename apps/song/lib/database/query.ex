defmodule Song.Query do
  @moduledoc false


  def find(spotify_id) do
    Song.Repo.get_by(Song, spotify_id: spotify_id)
  end

  def save(song_data = %Song{}) do
    song_data
    |> upsert()
  end

  defp upsert(song_data = %Song{}) do
    selectors = [spotify_id: song_data.spotify_id]
    old_song = Song.Repo.get_by(Song, selectors)
    case old_song do
      nil -> Song.Repo.insert!(song_data)
      song -> song
    end
  end

  def save_all([%Song{} | _] = songs) do
    Enum.map(songs, &(save(&1)))
  end

  def convert(song = %Spotify.Track{}) do
    %Song{
      spotify_id: song.id,
      album: song.album,
      artists: song.artists,
      available_markets: song.available_markets,
      disc_number: song.disc_number,
      duration_ms: song.duration_ms,
      explicit: song.explicit,
      external_ids: song.external_ids,
      href: song.href,
      is_playable: song.is_playable,
      linked_from: song.linked_from,
      name: song.name,
      popularity: song.popularity,
      preview_url: song.preview_url,
      track_number: song.track_number,
      type: song.type,
      uri: song.uri,
    }
  end
end