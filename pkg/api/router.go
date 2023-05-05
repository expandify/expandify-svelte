package api

import (
	"expandify-api/pkg/expandify"
	"expandify-api/pkg/expandify/authentication"
	"github.com/go-chi/chi/v5"
	"github.com/go-chi/chi/v5/middleware"
)

type Router interface {
	Router() chi.Router
}

type router struct {
	Login      Router
	User       Router
	jwt        Jwt
	repository expandify.Repository
	session    SessionController
}

func NewApi(spotifyClient expandify.SpotifyClient, repository expandify.Repository, encryptionSecret *[32]byte, jwtSecret *[]byte) Router {
	jwt := NewJwt(jwtSecret)
	session := NewSessionController(jwt)

	auth := authentication.New(spotifyClient, repository, encryptionSecret)

	return &router{
		Login:      NewLogin(auth, jwt),
		User:       NewUser(repository, session),
		jwt:        jwt,
		repository: repository,
		session:    session,
	}
}

func (a *router) Router() chi.Router {
	r := chi.NewRouter()
	r.Use(middleware.Logger)

	r.Mount("/auth", a.Login.Router())

	// Protected Routes
	r.Group(func(r chi.Router) {
		r.Use(a.jwt.Verifier())
		r.Use(a.jwt.Authenticator)

		r.Mount("/user", a.User.Router())
	})
	return r
}
