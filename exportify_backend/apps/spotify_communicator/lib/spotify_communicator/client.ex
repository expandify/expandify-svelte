defmodule SpotifyCommunicator.Client do

  def get(access_token, url) do
    HTTPoison.get(url, get_headers(access_token))
  end

  def put(access_token, url, body \\ "") do
    HTTPoison.put(url, body, put_headers(access_token))
  end

  def post(access_token, url, body \\ "") do
    HTTPoison.post(url, body, post_headers(access_token))
  end

  def delete(access_token, url) do
    HTTPoison.delete(url, delete_headers(access_token))
  end

  def get_headers(access_token) do
    [{"Authorization", "Bearer #{access_token}"}]
  end

  def put_headers(access_token) do
    [
      {"Authorization", "Bearer #{access_token}"},
      {"Content-Type", "application/json"}
    ]
  end

  def post_headers(conn), do: put_headers(conn)
  def delete_headers(conn), do: get_headers(conn)
end
