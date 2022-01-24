defmodule Query do
  @moduledoc false

  def find(spotify_id) do
    Playlists.Repo.get_by(Playlist, spotify_id: spotify_id)
  end

  def save(playlist_data = %Spotify.Playlist{}) do
    playlist_data
    |> convert()
    |> Playlists.Repo.insert()
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
