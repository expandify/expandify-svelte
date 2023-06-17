package api

import (
	"expandify-api/pkg/api/session_controller"
	"expandify-api/pkg/expandify/spotify_user"
	"expandify-api/pkg/spotify_client"
	"github.com/go-chi/chi/v5"
	"github.com/go-chi/render"
	"net/http"
)

type spotifyUserRouter struct {
	spotifyUser spotify_user.SpotifyUser
	session     session_controller.SessionController
}

func NewSpotifyUserRouter(
	spotifyClient spotify_client.SpotifyClient,
	repository spotify_user.Repository,
	session session_controller.SessionController) Router {

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
	user, err := a.spotifyUser.Get(a.session.CurrentUser(r))

	if err != nil {
		http.Error(w, http.StatusText(http.StatusBadRequest), http.StatusBadRequest)
		return
	}

	render.JSON(w, r, user)
}
