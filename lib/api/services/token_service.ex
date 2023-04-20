defmodule Api.TokenService do

  @salt "user auth"

  def sign_token(data) do
    Phoenix.Token.sign(ApiWeb.Endpoint, @salt, data)
  end

  def get_bearer(["bearer " <> token]), do: {:ok, token}
  def get_bearer(["Bearer " <> token]), do: {:ok, token}
  def get_bearer(_), do: {:error, "token not found"}


  def verify_token({:error, _} = err), do: err
  def verify_token({:ok, token}), do: Phoenix.Token.verify(ApiWeb.Endpoint, @salt, token)

  def get_user({:error, reason}), do: {:error, reason}
  def get_user({:ok, user_id}) do
    # case User.Query.find(user_id) do
    #   nil -> {:error, "No Credentials for the user were found"}
    #   user -> {:ok, user}
    # end
    {:ok, user_id}
  end

end
