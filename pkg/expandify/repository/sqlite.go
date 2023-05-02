package repository

import (
	"gorm.io/driver/sqlite"
)

func NewSQLiteRepository(file string) Repository {
	return NewGormRepository(sqlite.Open(file))
}
