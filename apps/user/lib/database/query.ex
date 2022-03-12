defmodule User.Query do
  @moduledoc false

  def find(spotify_id) do
    User.Repo.get_by(User, spotify_id: spotify_id)
  end

  def save(user_data = %User{}) do
    user_data
    |> upsert()
  end

  defp upsert(user_data = %User{}) do
    case find(user_data.spotify_id) do
      nil -> User.Repo.insert!(user_data)
      user ->
        Ecto.Changeset.change(user, access_token: user_data.access_token, refresh_token: user_data.refresh_token)
        |> User.Repo.update!()
    end
  end

  def convert(profile = %Spotify.Profile{}, credentials = %Spotify.Credentials{}) do
    %User{
      spotify_id: profile.id,
      access_token: credentials.access_token,
      refresh_token: credentials.refresh_token,
      country: profile.country,
      display_name: profile.display_name,
      email: profile.email,
      external_urls: profile.external_urls,
      followers: profile.followers,
      href: profile.href,
      images: profile.images,
      product: profile.product,
      type: profile.type,
      uri: profile.uri,
    }
  end
end
