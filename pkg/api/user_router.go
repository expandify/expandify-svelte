package api

import (
	"expandify-api/pkg/api/session_controller"
	"expandify-api/pkg/expandify/user"
	"expandify-api/pkg/spotify_client"
	"github.com/go-chi/chi/v5"
	"github.com/go-chi/render"
	"net/http"
	"net/url"
)

type userRouter struct {
	user    user.User
	session session_controller.SessionController
}

func NewUserRouter(
	spotifyClient spotify_client.SpotifyClient,
	repository user.Repository,
	encryptionSecret *[32]byte,
	session session_controller.SessionController) Router {

	return &userRouter{
		user:    user.New(spotifyClient, repository, encryptionSecret),
		session: session,
	}
}

func (a *userRouter) Router() chi.Router {
	r := chi.NewRouter()

	r.Get("/login", a.loginWithSpotify)
	r.Get("/callback", a.callbackForSpotify)

	return r
}

func (a *userRouter) loginWithSpotify(w http.ResponseWriter, r *http.Request) {
	clientRedirect := r.URL.Query().Get("redirect_url")
	http.Redirect(w, r, a.user.SpotifyLoginUrl(clientRedirect), http.StatusMovedPermanently)
}

func (a *userRouter) callbackForSpotify(w http.ResponseWriter, r *http.Request) {
	code := r.URL.Query().Get("code")
	state := r.URL.Query().Get("state")

	if code == "" {
		http.Error(w, "invalid code", http.StatusUnauthorized)
		return
	}
	redirectUrl, id, err := a.user.CompleteSpotifyLogin(code, state)
	if err != nil {
		http.Error(w, err.Error(), http.StatusUnauthorized)
		return
	}

	a.session.NewSession(w, id)

	if redirectUrl == "" {
		render.PlainText(w, r, "Success: Use 'jwt' cookie to access api.")
		return
	}

	_, err = url.ParseRequestURI(redirectUrl)
	if err != nil {
		http.Error(w, "invalid redirect url", http.StatusBadRequest)
		return
	}

	http.Redirect(w, r, redirectUrl, http.StatusMovedPermanently)
}
