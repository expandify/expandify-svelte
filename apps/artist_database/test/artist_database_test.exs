defmodule ArtistDatabaseTest do
  use ExUnit.Case
  doctest ArtistDatabase

  test "greets the world" do
    assert ArtistDatabase.hello() == :world
  end
end
