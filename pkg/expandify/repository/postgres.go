package repository

import (
	"fmt"
	"gorm.io/driver/postgres"
)

func NewPostgresRepository(host string, user string, password string, dbname string, port string) Repository {
	dsn := fmt.Sprintf("host=%s user=%s password=%s dbname=%s port=%s", host, user, password, dbname, port)
	return NewGormRepository(postgres.Open(dsn))
}
