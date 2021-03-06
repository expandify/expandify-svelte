defmodule SpotifyCommunicatorTest do
  use ExUnit.Case
  doctest SpotifyCommunicator

  test "greets the world" do
    assert SpotifyCommunicator.hello() == :world
  end
end
