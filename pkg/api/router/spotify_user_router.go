package router

import (
	"expandify-api/pkg/api"
	"expandify-api/pkg/api/session_controller"
	"expandify-api/pkg/expandify"
	"expandify-api/pkg/expandify/spotify_user"
	"github.com/go-chi/chi/v5"
	"github.com/go-chi/render"
	"net/http"
)

type spotifyUserRouter struct {
	spotifyUser spotify_user.SpotifyUser
	session     session_controller.SessionController
}

func NewSpotifyUserRouter(
	spotifyClient expandify.SpotifyClient,
	repository spotify_user.Repository,
	session session_controller.SessionController) api.Api {

	return &spotifyUserRouter{
		spotifyUser: spotify_user.New(spotifyClient, repository),
		session:     session,
	}
}

func (a *spotifyUserRouter) Router() chi.Router {
	r := chi.NewRouter()

	r.Get("/", a.get)

	return r
}

func (a *spotifyUserRouter) get(w http.ResponseWriter, r *http.Request) {
	currentUser, err := a.session.CurrentUser(r)
	if err != nil {
		http.Error(w, http.StatusText(http.StatusForbidden), http.StatusForbidden)
		return
	}

	user, err := a.spotifyUser.Get(currentUser)
	if err != nil {
		http.Error(w, http.StatusText(http.StatusBadRequest), http.StatusBadRequest)
		return
	}

	render.JSON(w, r, user)
}
