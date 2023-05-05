package gorm

import (
	"expandify-api/pkg/expandify"
	"expandify-api/pkg/expandify/storage/models"
	"gorm.io/gorm"
	"log"
)

type gormRepository struct {
	database *gorm.DB
}

func NewGormRepository(dialector gorm.Dialector) expandify.Repository {

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

	return &gormRepository{database: db}
}

func (p *gormRepository) GetUser(id string) (*expandify.User, bool) {
	var user = models.User{ID: id}
	result := p.database.First(&user)
	if result.Error != nil {
		return nil, false
	}

	return user.Convert(), true
}

func (p *gormRepository) SaveUser(user *expandify.User) {
	model := models.NewUser(user)
	p.database.Save(model)
}

func (p *gormRepository) GetSpotifyUser(id string) (*expandify.SpotifyUser, bool) {
	var spotifyUser = models.SpotifyUser{ID: id}
	result := p.database.First(&spotifyUser)
	if result.Error != nil {
		return nil, false
	}

	return spotifyUser.Convert(), true
}

func (p *gormRepository) SaveSpotifyUser(spotifyUser *expandify.SpotifyUser) {
	model := models.NewSpotifyUser(spotifyUser)
	p.database.Save(model)
}
