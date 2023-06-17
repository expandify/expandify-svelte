package session_controller

import (
	"context"
	"errors"
	"github.com/go-chi/jwtauth"
	jwtoken "github.com/lestrrat-go/jwx/jwt"
	"net/http"
)

const jwtAlgorithm = "HS256"
const claimsKey = "user_id"

func (s *sessionController) createJwt(userId string) (string, error) {
	_, jwt, err := s.jwtAuth.Encode(map[string]interface{}{claimsKey: userId})
	if err != nil {
		return "", err
	}
	return jwt, nil
}

func (s *sessionController) jwtVerifier() func(http.Handler) http.Handler {
	return jwtauth.Verifier(s.jwtAuth)
}

func (s *sessionController) jwtAuthenticator(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {

		_, err := s.parseJwt(r.Context())
		if err != nil {
			http.Error(w, err.Error(), http.StatusUnauthorized)
			return
		}
		// Token is authenticated, pass it through

		next.ServeHTTP(w, r)
	})
}

func (s *sessionController) parseJwt(ctx context.Context) (string, error) {
	token, claims, err := jwtauth.FromContext(ctx)
	if err != nil {
		return "", err
	}

	if token == nil {
		return "", errors.New("jwt is missing")
	}

	err = jwtoken.Validate(token)
	if err != nil {
		return "", err
	}

	claim := claims[claimsKey]
	userId, ok := claim.(string)
	if !ok {
		return "", errors.New("jwt is invalid")
	}
	return userId, nil
}
