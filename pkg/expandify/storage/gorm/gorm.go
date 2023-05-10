package gorm

import (
	"expandify-api/pkg/expandify/spotify_user"
	"expandify-api/pkg/expandify/storage/models"
	"expandify-api/pkg/expandify/user"
	"gorm.io/gorm"
	"log"
)

type Connection interface {
	NewUserRepository() user.Repository
	NewSpotifyUserRepository() spotify_user.Repository
}

type connection struct {
	database *gorm.DB
}

func NewGormConnection(dialector gorm.Dialector) Connection {

	db, err := gorm.Open(dialector, &gorm.Config{})
	if err != nil {
		log.Fatalln("Error connecting to database")
	}

	err = db.AutoMigrate(
		&models.SpotifyUser{},
		&models.User{},
	)
	if err != nil {
		log.Fatalln("Error migrating database")
	}

	return &connection{
		database: db,
	}
}

func (c *connection) NewUserRepository() user.Repository {
	return &gormUserRepository{database: c.database}
}

func (c *connection) NewSpotifyUserRepository() spotify_user.Repository {
	return &gormSpotifyUserRepository{database: c.database}
}
