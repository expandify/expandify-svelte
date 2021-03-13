defmodule API.ControllerHelper do

  def to_map(struct) when is_struct(struct) do

    struct
    |> Map.from_struct
    |> Map.to_list
    |> Enum.reduce(%{}, fn {key, value}, acc ->
      Map.put(acc, key, to_map(value))
    end)
  end

  def to_map(no_struct), do: no_struct
end
