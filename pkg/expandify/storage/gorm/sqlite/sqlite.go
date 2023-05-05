package sqlite

import (
	"expandify-api/pkg/expandify"
	"expandify-api/pkg/expandify/storage/gorm"
	"gorm.io/driver/sqlite"
)

func NewSQLiteRepository(file string) expandify.Repository {
	return gorm.NewGormRepository(sqlite.Open(file))
}
