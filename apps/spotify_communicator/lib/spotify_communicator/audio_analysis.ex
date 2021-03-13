defmodule SpotifyCommunicator.AudioAnalysis do

  @derive Jason.Encoder
  defstruct ~w[
    bars
    beats
    meta
    sections
    segments
    tatums
    track
  ]a
end
