package user

import (
	"expandify-api/pkg/expandify"
)

type Repository interface {
	Get(id string) (*expandify.User, error)
	Save(user *expandify.User)
}
