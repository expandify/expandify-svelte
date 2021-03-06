defmodule DatabaseConnector.Query do
  defmacro __using__(_) do

    quote do

      alias __MODULE__, as: Repo

        def get_or_insert(model) do
          insert_validation =
            model
            |> model.__struct__.changeset()
            |> Repo.insert()

          case insert_validation do
            {:error, _} -> model.__struct__
                           |> Repo.get_by(spotify_id: model.spotify_id)
                           |> validate_get_by
            {:ok, _} -> insert_validation
          end
        end


        def get_or_insert_all(models) when models == [] do [] end
        def get_or_insert_all(models) do
          models
          |> Enum.map(&(Repo.insert(&1.__struct__.changeset(&1))))

          models
          |> Enum.map(&(Repo.get_by(&1.__struct__, spotify_id: &1.spotify_id)))
        end

        def upsert(model, on_conflict) do
          case Repo.get_by(model.__struct__, spotify_id: model.spotify_id) do
            nil -> model
            db_model -> db_model
          end
          |> model.__struct__.changeset(on_conflict)
          |> Repo.insert_or_update
        end

        defp validate_get_by(nil), do: {:error, nil}
        defp validate_get_by(model), do: {:ok, model}

        def get_by_spotify_id(module, spotify_id) do
          module
          |> Repo.get_by(spotify_id: spotify_id)
          |> validate_get_by
        end

    end
  end
end
