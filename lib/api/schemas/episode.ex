defmodule Episode do
  use Api.Schema

  schema "episodes" do
    field :audio_preview_url, :string
    field :description, :string
    field :html_description, :string
    field :duration_ms, :integer
    field :explicit, :boolean
    embeds_one :external_urls, ExternalUrls
    field :href, :string
    field :spotify_id, :string
    embeds_many :images, Image
    field :is_externally_hosted, :string
    field :is_playable, :boolean
    field :languages, {:array, :string}
    field :name, :string
    field :release_date, :string
    field :release_date_precision, :string
    embeds_one :resume_point, ResumePoint
    field :type, Ecto.Enum, values: [:episode]
    field :uri, :string
    embeds_one :restrictions, Restrictions
    embeds_one :simple_show, EpisodeShow do
      field :description, :string
      field :href, :string
      field :spotify_id, :string
      embeds_many :images, Image
      field :name, :string
      field :uri, :string
      field :total_episodes, :integer
    end

  end

end
