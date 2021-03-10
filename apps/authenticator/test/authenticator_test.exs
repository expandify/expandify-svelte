defmodule AuthenticatorTest do
  use ExUnit.Case
  doctest Authenticator

  test "greets the world" do
    assert Authenticator.hello() == :world
  end
end
