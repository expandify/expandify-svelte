package repository

import (
	"expandify-api/pkg/expandify/models"
	"strings"
)

const TypePostgres = "postgres"
const TypeSQLite = "sqlite"

type Config struct {
	StorageType string

	PostgresHost     string
	PostgresUser     string
	PostgresPassword string
	PostgresDbName   string
	PostgresPort     string

	SQLiteFile string
}

type Repository interface {
	GetUser(id string) (*models.User, bool)
	SaveUser(user *models.User)
	GetSpotifyUser(id string) (*models.SpotifyUser, bool)
	SaveSpotifyUser(spotifyUser *models.SpotifyUser)
}

func New(repositoryConfig Config) Repository {
	switch strings.ToLower(repositoryConfig.StorageType) {
	case TypePostgres:
		return NewPostgresRepository(
			repositoryConfig.PostgresHost,
			repositoryConfig.PostgresUser,
			repositoryConfig.PostgresPassword,
			repositoryConfig.PostgresDbName,
			repositoryConfig.PostgresPort)
	case TypeSQLite:
		return NewSQLiteRepository(repositoryConfig.SQLiteFile)
	default:
		return nil
	}
}
