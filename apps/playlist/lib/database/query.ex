defmodule Playlist.Query do
  @moduledoc false

  def find(spotify_id) do
    Playlist.Repo.get_by(Playlist, spotify_id: spotify_id)
  end

  def save(playlist_data = %Playlist{}) do
    playlist_data
    |> upsert()
  end

  defp upsert(playlist_data = %Playlist{}) do
    selectors = [spotify_id: playlist_data.spotify_id, snapshot_id: playlist_data.snapshot_id]
    old_playlist = Playlist.Repo.get_by(Playlist, selectors)
    case old_playlist do
      nil -> Playlist.Repo.insert!(playlist_data)
      playlist -> playlist
    end
  end

  def save_all([%Playlist{} | _] = playlists) do
    Enum.map(playlists, &(save(&1)))
  end

  def convert(playlist = %Spotify.Playlist{}) do
    %Playlist{
      collaborative: playlist.collaborative,
      description: playlist.description,
      external_urls: playlist.external_urls,
      followers: playlist.followers,
      href: playlist.href,
      spotify_id: playlist.id,
      images: playlist.images,
      name: playlist.name,
      owner: playlist.owner,
      public: playlist.public,
      snapshot_id: playlist.snapshot_id,
      tracks: playlist.tracks,
      type: playlist.type,
      uri: playlist.uri,
    }
  end
end
