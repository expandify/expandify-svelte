defmodule DatabaseConnectorTest do
  use ExUnit.Case
  doctest DatabaseConnector

  test "greets the world" do
    assert DatabaseConnector.hello() == :world
  end
end
