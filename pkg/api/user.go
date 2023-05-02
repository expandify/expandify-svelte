package api

import (
	"expandify-api/pkg/expandify/repository"
	"github.com/go-chi/chi/v5"
	"github.com/go-chi/render"
	"net/http"
)

type user struct {
	repository repository.Repository
	session    SessionController
}

func NewUser(repository repository.Repository, session SessionController) Router {
	return &user{
		repository: repository,
		session:    session,
	}
}

func (a *user) Router() chi.Router {
	r := chi.NewRouter()

	r.Get("/", a.get)

	return r
}

func (a *user) get(w http.ResponseWriter, r *http.Request) {
	user, success := a.repository.GetSpotifyUser(a.session.CurrentUser(r))

	if !success {
		http.Error(w, http.StatusText(http.StatusBadRequest), http.StatusBadRequest)
		return
	}

	render.JSON(w, r, user)
}
