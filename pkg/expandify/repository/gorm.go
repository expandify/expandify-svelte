package repository

import (
	"expandify-api/pkg/expandify/models"
	"gorm.io/gorm"
	"log"
)

type gormRepository struct {
	database *gorm.DB
}

func NewGormRepository(dialector gorm.Dialector) Repository {

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

func (p *gormRepository) GetUser(id string) (*models.User, bool) {
	var user = models.User{ID: id}
	result := p.database.First(user)
	if result.Error != nil {
		return nil, false
	}

	return &user, true
}

func (p *gormRepository) SaveUser(user *models.User) {

	p.database.Save(user)
}

func (p *gormRepository) GetSpotifyUser(id string) (*models.SpotifyUser, bool) {
	var spotifyUser = models.SpotifyUser{ID: id}
	result := p.database.First(spotifyUser)
	if result.Error != nil {
		return nil, false
	}

	return &spotifyUser, true
}

func (p *gormRepository) SaveSpotifyUser(spotifyUser *models.SpotifyUser) {
	p.database.Save(spotifyUser)
}
