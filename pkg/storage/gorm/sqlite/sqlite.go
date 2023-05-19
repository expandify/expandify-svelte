package sqlite

import (
	"expandify-api/pkg/storage/gorm"
	"gorm.io/driver/sqlite"
)

func NewSQLiteRepository(file string) gorm.Connection {
	return gorm.NewGormConnection(sqlite.Open(file))
}
