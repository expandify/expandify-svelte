package api

import (
	"expandify-api/pkg/api/session_controller"
	"expandify-api/pkg/expandify/spotify_user"
	"github.com/go-chi/chi/v5"
	"github.com/go-chi/render"
	"net/http"
)

type spotifyUserRouter struct {
	spotifyUserRepository spotify_user.Repository
	session               session_controller.SessionController
}

func NewSpotifyUserRouter(spotifyUserRepository spotify_user.Repository, session session_controller.SessionController) Router {
	return &spotifyUserRouter{
		spotifyUserRepository: spotifyUserRepository,
		session:               session,
	}
}

func (a *spotifyUserRouter) Router() chi.Router {
	r := chi.NewRouter()

	r.Get("/", a.get)

	return r
}

func (a *spotifyUserRouter) get(w http.ResponseWriter, r *http.Request) {
	user, err := a.spotifyUserRepository.Get(a.session.CurrentUser(r))

	if err != nil {
		http.Error(w, http.StatusText(http.StatusBadRequest), http.StatusBadRequest)
		return
	}

	render.JSON(w, r, user)
}
