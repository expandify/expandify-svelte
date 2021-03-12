defmodule SongDatabaseTest do
  use ExUnit.Case
  doctest SongDatabase

  test "greets the world" do
    assert SongDatabase.hello() == :world
  end
end
