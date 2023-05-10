package internal

import (
	"expandify-api/pkg/expandify/storage/gorm"
	"expandify-api/pkg/expandify/storage/gorm/postgres"
	"expandify-api/pkg/expandify/storage/gorm/sqlite"
	"strings"
)

const TypePostgres = "postgres"
const TypeSQLite = "sqlite"

func NewRepositoryConnection(repositoryConfig *Config) gorm.Connection {
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
