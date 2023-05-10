package api

import (
	"expandify-api/pkg/api/request"
	"expandify-api/pkg/expandify"
	"expandify-api/pkg/expandify/authentication"
	"expandify-api/pkg/expandify/spotify_user"
	expandify_user "expandify-api/pkg/expandify/user"
	"github.com/go-chi/chi/v5"
	"github.com/go-chi/chi/v5/middleware"
)

type Router interface {
	Router() chi.Router
}

type router struct {
	Login                 Router
	User                  Router
	jwt                   request.Jwt
	userRepository        expandify_user.Repository
	spotifyUserRepository spotify_user.Repository
	session               request.SessionController
}

func NewApi(spotifyClient expandify.SpotifyClient, userRepository expandify_user.Repository, spotifyUserRepository spotify_user.Repository, encryptionSecret *[32]byte, jwtSecret *[]byte) Router {
	jwt := request.NewJwt(jwtSecret)
	session := request.NewSessionController(jwt)

	auth := authentication.New(spotifyClient, userRepository, spotifyUserRepository, encryptionSecret)

	return &router{
		Login:                 NewLogin(auth, jwt),
		User:                  NewUser(spotifyUserRepository, session),
		jwt:                   jwt,
		userRepository:        userRepository,
		spotifyUserRepository: spotifyUserRepository,
		session:               session,
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
