defmodule SpotifyCommunicator.Helpers do
  @moduledoc false

  def query_string(nil), do: ""
  def query_string([]), do: ""
  def query_string(params), do: "?" <> URI.encode_query(params)

  def to_struct(kind, attrs) do
    struct = struct(kind)

    struct
    |> Map.to_list()
    |> Enum.reduce(struct, fn {key, _}, acc ->
      result = Map.fetch(attrs, Atom.to_string(key))

      case result do
        {:ok, value} -> %{acc | key => value}
        :error -> acc
      end
    end)
  end
end
