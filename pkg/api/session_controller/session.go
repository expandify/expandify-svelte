package session_controller

import (
	"github.com/go-chi/jwtauth"
	"net/http"
)

type SessionController interface {
	CurrentUser(r *http.Request) string
	NewSession(w http.ResponseWriter, id string)
	VerifyAndAuthenticate(next http.Handler) http.Handler
}

type sessionController struct {
	jwtAuth *jwtauth.JWTAuth
}

func New(jwtSecret *[]byte) SessionController {
	return &sessionController{
		jwtAuth: jwtauth.New(jwtAlgorithm, *jwtSecret, nil),
	}
}

func (s *sessionController) CurrentUser(r *http.Request) string {
	user, err := s.parseJwt(r.Context())
	if err != nil {
		return ""
	}
	return user
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
