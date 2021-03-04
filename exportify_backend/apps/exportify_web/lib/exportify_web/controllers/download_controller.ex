defmodule ExportifyWeb.DownloadController do
  @moduledoc """
    Responsible for Downloading the library.
  """
  use ExportifyWeb, :controller

  @doc """
    Downloads the library.
  """
  def download(conn, _params) do

    library_zentries =
    library_to_zentry_list(conn, :playlists_pid, "playlists") ++
    library_to_zentry_list(conn, :songs_pid, "songs") ++
    library_to_zentry_list(conn, :artists_pid, "artists") ++
    library_to_zentry_list(conn, :albums_pid, "albums")



    conn = conn
    |> put_resp_content_type("application/zip")
    |> put_resp_header("content-disposition", "attachment; filename=library.zip")
    |> send_chunked(200)

    Zstream.zip(library_zentries)
    |> Enum.reduce_while(conn, fn (content, conn) ->
        case Plug.Conn.chunk(conn, content) do
          {:ok, conn} ->
            {:cont, conn}
          {:error, :closed} ->
            {:halt, conn}
        end
      end)
  end

  @doc false
  defp library_to_zentry_list(conn, pid, folder_name) do
    {:ok, p_id} =
    get_session(conn, :library_pids)
    |> ExportifyLocalStorage.Library.get_pid(pid)

    zentries =
    p_id
    |> ExportifyLocalStorage.LibraryStorage.get_items()
    |> Enum.map(fn item -> data_to_zentry(item, folder_name) end)
    zentry_list = [Zstream.entry(folder_name <> "/", []) | zentries]
    zentry_list
  end

  @doc false
  defp data_to_zentry(data, folder_name) do
    base_name = folder_name <> "/" <> Zarex.sanitize(String.downcase(data.name))
    file_name = make_unique_file_name(base_name) <> ".txt"

    {:ok, string_stream} =
      data_to_string(data)
      |> StringIO.open()

    Zstream.entry(file_name, IO.binstream(string_stream, :line))
  end

  @doc false
  defp make_unique_file_name(base_name, amount \\ 0) do
    if not File.exists?(base_name <> ".txt") do
      base_name
    else
      make_unique_file_name("#{base_name} (#{to_string(amount+1)})")
    end
  end

  @doc false
  defp data_to_string(data) do
    opts_list = [printable_limit: :infinity, limit: :infinity]
    opts = struct(Inspect.Opts, opts_list)
    doc = Inspect.Algebra.group(Inspect.Algebra.to_doc(data, opts))
    chardata = Inspect.Algebra.format(doc, opts.width)
    Enum.join(chardata, "")
  end


end
