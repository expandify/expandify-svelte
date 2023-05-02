package api

import (
	"expandify-api/pkg/expandify/authentication"
	"github.com/go-chi/chi/v5"
	"github.com/go-chi/chi/v5/middleware"
	"github.com/go-chi/jwtauth"
)

type api struct {
	Login   Router
	jwtAuth *jwtauth.JWTAuth
}

func NewApi(auth authentication.Auth, jwtAuth *jwtauth.JWTAuth) Router {
	return &api{
		Login:   NewLogin(auth, jwtAuth),
		jwtAuth: jwtAuth,
	}
}

func (a *api) Router() chi.Router {
	r := chi.NewRouter()
	r.Use(middleware.Logger)

	r.Mount("/auth", a.Login.Router())
	return r
}
