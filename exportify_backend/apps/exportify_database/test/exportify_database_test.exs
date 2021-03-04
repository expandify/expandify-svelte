defmodule ExportifyDatabaseTest do
  use ExUnit.Case
  doctest ExportifyDatabase

  test "greets the world" do
    assert ExportifyDatabase.hello() == :world
  end
end
