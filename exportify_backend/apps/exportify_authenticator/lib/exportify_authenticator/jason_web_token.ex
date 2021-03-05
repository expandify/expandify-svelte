defmodule ExportifyAuthenticator.JasonWebToken do
  use Joken.Config

  @impl Joken.Config
  def token_config() do
    default_claims(skip: [:iss, :aud, :exp])
  end

  def create_claims(data \\ %{}) do
    # %{ "iss" => data.spotify_id }
    %{"claims" => data}
  end

  def create(data \\ %{}) do
    extra_claims = create_claims(data)
    {:ok, token, _} = generate_and_sign(extra_claims)
    token
  end

  def verify(token) do
    verify_and_validate(token)
  end

end
