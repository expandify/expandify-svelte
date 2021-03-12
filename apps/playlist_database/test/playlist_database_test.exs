defmodule PlaylistDatabaseTest do
  use ExUnit.Case
  doctest PlaylistDatabase

  test "greets the world" do
    assert PlaylistDatabase.hello() == :world
  end
end
