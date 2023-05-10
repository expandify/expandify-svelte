package request

import (
	"net/http"
)

type SessionController interface {
	CurrentUser(r *http.Request) string
}

type sessionController struct {
	jwt Jwt
}

func NewSessionController(jwt Jwt) SessionController {
	return &sessionController{
		jwt: jwt,
	}
}

func (s *sessionController) CurrentUser(r *http.Request) string {
	user, err := s.jwt.Parse(r.Context())
	if err != nil {
		return ""
	}
	return user
}
