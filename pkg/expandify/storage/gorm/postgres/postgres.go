package postgres

import (
	"expandify-api/pkg/expandify/storage/gorm"
	"fmt"
	"gorm.io/driver/postgres"
)

func NewPostgresRepository(host string, user string, password string, dbname string, port string) gorm.Connection {
	dsn := fmt.Sprintf("host=%s user=%s password=%s dbname=%s port=%s", host, user, password, dbname, port)
	return gorm.NewGormConnection(postgres.Open(dsn))
}
