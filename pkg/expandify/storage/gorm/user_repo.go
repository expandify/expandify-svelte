package gorm

import (
	"expandify-api/pkg/expandify"
	"expandify-api/pkg/expandify/storage/models"
	"gorm.io/gorm"
)

type gormUserRepository struct {
	database *gorm.DB
}

func (p *gormUserRepository) Get(id string) (*expandify.User, error) {
	var u = models.User{ID: id}
	result := p.database.First(&u)
	if result.Error != nil {
		return nil, result.Error
	}

	return u.Convert(), nil
}

func (p *gormUserRepository) Save(user *expandify.User) {
	model := models.NewUser(user)
	p.database.Save(model)
}
