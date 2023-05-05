package internal

import (
	"expandify-api/pkg/expandify"
	"expandify-api/pkg/expandify/storage/gorm/postgres"
	"expandify-api/pkg/expandify/storage/gorm/sqlite"
	"strings"
)

const TypePostgres = "postgres"
const TypeSQLite = "sqlite"

func NewRepository(repositoryConfig *Config) expandify.Repository {
	switch strings.ToLower(repositoryConfig.StorageType) {
	case TypePostgres:
		return postgres.NewPostgresRepository(
			repositoryConfig.PostgresHost,
			repositoryConfig.PostgresUser,
			repositoryConfig.PostgresPassword,
			repositoryConfig.PostgresDbName,
			repositoryConfig.PostgresPort)
	case TypeSQLite:
		return sqlite.NewSQLiteRepository(repositoryConfig.SQLiteFile)
	default:
		return nil
	}
}
