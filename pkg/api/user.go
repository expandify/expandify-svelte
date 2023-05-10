package api

import (
	"expandify-api/pkg/api/request"
	"expandify-api/pkg/expandify/spotify_user"
	"github.com/go-chi/chi/v5"
	"github.com/go-chi/render"
	"net/http"
)

type user struct {
	spotifyUserRepository spotify_user.Repository
	session               request.SessionController
}

func NewUser(spotifyUserRepository spotify_user.Repository, session request.SessionController) Router {
	return &user{
		spotifyUserRepository: spotifyUserRepository,
		session:               session,
	}
}

func (a *user) Router() chi.Router {
	r := chi.NewRouter()

	r.Get("/", a.get)

	return r
}

func (a *user) get(w http.ResponseWriter, r *http.Request) {
	user, err := a.spotifyUserRepository.Get(a.session.CurrentUser(r))

	if err != nil {
		http.Error(w, http.StatusText(http.StatusBadRequest), http.StatusBadRequest)
		return
	}

	render.JSON(w, r, user)
}
