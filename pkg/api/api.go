package api

import (
	"expandify-api/pkg/api/router"
	"expandify-api/pkg/api/session_controller"
	"expandify-api/pkg/expandify"
	"expandify-api/pkg/expandify/spotify_user"
	"expandify-api/pkg/expandify/user"
	"github.com/go-chi/chi/v5"
	"github.com/go-chi/chi/v5/middleware"
)

type Api interface {
	Router() chi.Router
}

type api struct {
	UserRouter        Api
	SpotifyUserRouter Api
	session           session_controller.SessionController
}

func NewApi(
	spotifyClient expandify.SpotifyClient,
	userRepository user.Repository,
	spotifyUserRepository spotify_user.Repository,
	encryptionSecret *[32]byte,
	jwtSecret *[]byte) Api {

	session := session_controller.New(jwtSecret, userRepository)

	return &api{
		UserRouter:        router.NewUserRouter(spotifyClient, userRepository, encryptionSecret, session),
		SpotifyUserRouter: router.NewSpotifyUserRouter(spotifyClient, spotifyUserRepository, session),
		session:           session,
	}
}

func (a *api) Router() chi.Router {
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
