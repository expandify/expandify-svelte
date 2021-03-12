defmodule LibraryDatabaseTest do
  use ExUnit.Case
  doctest LibraryDatabase

  test "greets the world" do
    assert LibraryDatabase.hello() == :world
  end
end
