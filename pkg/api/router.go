package api

import "github.com/go-chi/chi/v5"

type Router interface {
	Router() chi.Router
}
