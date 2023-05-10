package sqlite

import (
	"expandify-api/pkg/expandify/storage/gorm"
	"gorm.io/driver/sqlite"
)

func NewSQLiteRepository(file string) gorm.Connection {
	return gorm.NewGormConnection(sqlite.Open(file))
}
