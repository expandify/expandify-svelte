defmodule ExportifyWeb.Application do
  # See https://hexdocs.pm/elixir/Application.html
  # for more information on OTP Applications
  @moduledoc false

  use Application

  def start(_type, _args) do
    children = [
      {Phoenix.PubSub, name: ExportifyWeb.PubSub},
      # Start the Telemetry supervisor
      ExportifyWeb.Telemetry,
      # Start the Endpoint (http/https)
      ExportifyWeb.Endpoint
      # Start a worker by calling: ExportifyWeb.Worker.start_link(arg)
      # {ExportifyWeb.Worker, arg}
    ]

    # See https://hexdocs.pm/elixir/Supervisor.html
    # for other strategies and supported options
    opts = [strategy: :one_for_one, name: ExportifyWeb.Supervisor]
    Supervisor.start_link(children, opts)
  end

  # Tell Phoenix to update the endpoint configuration
  # whenever the application is updated.
  def config_change(changed, _new, removed) do
    ExportifyWeb.Endpoint.config_change(changed, removed)
    :ok
  end
end
