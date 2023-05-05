package postgres

import (
	"expandify-api/pkg/expandify"
	"expandify-api/pkg/expandify/storage/gorm"
	"fmt"
	"gorm.io/driver/postgres"
)

func NewPostgresRepository(host string, user string, password string, dbname string, port string) expandify.Repository {
	dsn := fmt.Sprintf("host=%s user=%s password=%s dbname=%s port=%s", host, user, password, dbname, port)
	return gorm.NewGormRepository(postgres.Open(dsn))
}
