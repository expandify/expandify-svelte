defmodule ExportifyLocalStorage.User do
  @moduledoc """
      Responsible for retrieving and saving user Data.
  """
  alias ExportifySpotify.RequestHandler.AccessData
  @user_profile_url "https://api.spotify.com/v1/me"

  @doc """
    Starts the storage process for the user data.
    Starts a task to retrieve the data.
    The user is identified by the given access token.
  """
  def start_download(token) do
    expires_in = token.expires_in
    {:ok, user_storage} = ExportifyLocalStorage.LibraryStorage.start_link(expires_in)
    access_data = %AccessData{access_token: token.access_token, refresh_token: token.refresh_token, storage_pid: user_storage, expires_in: token.expires_in}
    Task.start_link(fn -> ExportifyLocalStorage.Item.backup_from_link(@user_profile_url, access_data, &(process_item/2)) end)
    user_storage
  end


  @doc """
    Processes a response.
    Updates the storage process with the user data.
    Returns the processed item in a list.
  """
  def process_item(response, %AccessData{} = user) do
    user_info = ExportifySpotify.User.get_info(response, user)

    processed_item = ExportifyLocalStorage.LibraryStorage.update_item(user.storage_pid, user_info)

    {:ok, [processed_item], nil}
  end

end
