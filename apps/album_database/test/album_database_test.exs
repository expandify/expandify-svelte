defmodule AlbumDatabaseTest do
  use ExUnit.Case
  doctest AlbumDatabase

  test "greets the world" do
    assert AlbumDatabase.hello() == :world
  end
end
