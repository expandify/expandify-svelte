package api

import (
	"expandify-api/pkg/api/request"
	"expandify-api/pkg/expandify/authentication"
	"github.com/go-chi/chi/v5"
	"github.com/go-chi/render"
	"net/http"
	"net/url"
)

type login struct {
	auth authentication.Auth
	jwt  request.Jwt
}

func NewLogin(auth authentication.Auth, jwt request.Jwt) Router {
	return &login{
		auth: auth,
		jwt:  jwt,
	}
}

func (a *login) Router() chi.Router {
	r := chi.NewRouter()

	r.Get("/login", a.login)
	r.Get("/callback", a.callback)

	return r
}

func (a *login) login(w http.ResponseWriter, r *http.Request) {
	clientRedirect := r.URL.Query().Get("redirect_url")
	http.Redirect(w, r, a.auth.Url(clientRedirect), http.StatusMovedPermanently)
}

func (a *login) callback(w http.ResponseWriter, r *http.Request) {
	code := r.URL.Query().Get("code")
	state := r.URL.Query().Get("state")

	if code == "" {
		http.Error(w, "invalid code", http.StatusUnauthorized)
		return
	}
	redirectUrl, id, err := a.auth.CompleteAuth(code, state)
	if err != nil {
		http.Error(w, err.Error(), http.StatusUnauthorized)
		return
	}

	jwt, err := a.jwt.Create(id)
	if err != nil {
		http.Error(w, err.Error(), http.StatusUnauthorized)
		return
	}
	http.SetCookie(w, &http.Cookie{Name: "jwt", Value: jwt, HttpOnly: false})

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
