package api

import (
	"expandify-api/pkg/api/session_controller"
	"expandify-api/pkg/expandify/spotify_user"
	"expandify-api/pkg/expandify/user"
	"expandify-api/pkg/spotify_client"
	"github.com/go-chi/chi/v5"
	"github.com/go-chi/chi/v5/middleware"
)

type Router interface {
	Router() chi.Router
}

type router struct {
	UserRouter        Router
	SpotifyUserRouter Router
	session           session_controller.SessionController
}

func NewApiRouter(
	spotifyClient spotify_client.SpotifyClient,
	userRepository user.Repository,
	spotifyUserRepository spotify_user.Repository,
	encryptionSecret *[32]byte,
	jwtSecret *[]byte) Router {

	session := session_controller.New(jwtSecret)

	return &router{
		UserRouter:        NewUserRouter(spotifyClient, userRepository, encryptionSecret, session),
		SpotifyUserRouter: NewSpotifyUserRouter(spotifyClient, spotifyUserRepository, session),
		session:           session,
	}
}

func (a *router) Router() chi.Router {
	r := chi.NewRouter()
	r.Use(middleware.Logger)

	r.Mount("/auth", a.UserRouter.Router())

	// Protected Routes
	r.Group(func(r chi.Router) {
		r.Use(a.session.VerifyAndAuthenticate)

		r.Mount("/user", a.SpotifyUserRouter.Router())
	})
	return r
}
