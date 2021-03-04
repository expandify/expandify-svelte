defmodule ExportifyAuthenticator.JasonWebToken do
  use Joken.Config

  @impl Joken.Config
  def token_config() do
    default_claims(skip: [:iss, :aud])
  end

  def create_claims(%{} = data) do
    # %{ "iss" => data.spotify_id }
    data
  end

end
