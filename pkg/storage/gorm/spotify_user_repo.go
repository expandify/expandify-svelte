package gorm

import (
	"expandify-api/pkg/expandify"
	"expandify-api/pkg/storage/models"
	"gorm.io/gorm"
)

type gormSpotifyUserRepository struct {
	database *gorm.DB
}

func (p *gormSpotifyUserRepository) Get(id string) (*expandify.SpotifyUser, error) {
	var spotifyUser = models.SpotifyUser{ID: id}
	result := p.database.First(&spotifyUser)
	if result.Error != nil {
		return nil, result.Error
	}

	return spotifyUser.Convert(), nil
}

func (p *gormSpotifyUserRepository) Save(user *expandify.SpotifyUser) {
	model := models.NewSpotifyUser(user)
	p.database.Save(model)
}

func (p *gormSpotifyUserRepository) GetSync(id string) (*expandify.Sync, error) {
	//TODO implement me
	id = id
	panic("implement me")
}

func (p *gormSpotifyUserRepository) SaveSync(id string, sync *expandify.Sync) {
	id = id
	sync = sync
	//TODO implement me
	panic("implement me")
}
