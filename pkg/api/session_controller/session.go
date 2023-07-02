package session_controller

import (
	"expandify-api/pkg/expandify"
	"expandify-api/pkg/expandify/user"
	"github.com/go-chi/jwtauth"
	"net/http"
)

type SessionController interface {
	CurrentUser(r *http.Request) (*expandify.User, error)
	NewSession(w http.ResponseWriter, id string)
	VerifyAndAuthenticate(next http.Handler) http.Handler
}

type sessionController struct {
	jwtAuth        *jwtauth.JWTAuth
	userRepository user.Repository
}

func New(jwtSecret *[]byte, repository user.Repository) SessionController {
	return &sessionController{
		jwtAuth:        jwtauth.New(jwtAlgorithm, *jwtSecret, nil),
		userRepository: repository,
	}
}

func (s *sessionController) CurrentUser(r *http.Request) (*expandify.User, error) {
	id, err := s.parseJwt(r.Context())
	if err != nil {
		return nil, err
	}
	return s.userRepository.Get(id)
}

func (s *sessionController) NewSession(w http.ResponseWriter, id string) {
	jwt, err := s.createJwt(id)
	if err != nil {
		http.Error(w, err.Error(), http.StatusUnauthorized)
		return
	}
	http.SetCookie(w, &http.Cookie{Name: "jwt", Value: jwt, HttpOnly: false})
}

func (s *sessionController) VerifyAndAuthenticate(next http.Handler) http.Handler {
	return s.jwtVerifier()(s.jwtAuthenticator(next))
}
