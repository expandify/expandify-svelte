package request

import (
	"context"
	"errors"
	"github.com/go-chi/jwtauth"
	jwtoken "github.com/lestrrat-go/jwx/jwt"
	"net/http"
)

const jwtAlgorithm = "HS256"
const claimsKey = "user_id"

type Jwt interface {
	Create(userId string) (string, error)
	Parse(ctx context.Context) (string, error)
	Verifier() func(http.Handler) http.Handler
	Authenticator(next http.Handler) http.Handler
}

type jwt struct {
	jwtAuth *jwtauth.JWTAuth
}

func NewJwt(secret *[]byte) Jwt {
	return &jwt{
		jwtAuth: jwtauth.New(jwtAlgorithm, *secret, nil),
	}
}

func (j *jwt) Create(userId string) (string, error) {
	_, jwt, err := j.jwtAuth.Encode(map[string]interface{}{claimsKey: userId})
	if err != nil {
		return "", err
	}
	return jwt, nil
}

func (j *jwt) Verifier() func(http.Handler) http.Handler {
	return jwtauth.Verifier(j.jwtAuth)
}

func (j *jwt) Authenticator(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {

		_, err := j.Parse(r.Context())
		if err != nil {
			http.Error(w, err.Error(), http.StatusUnauthorized)
			return
		}
		// Token is authenticated, pass it through

		next.ServeHTTP(w, r)
	})
}

func (j *jwt) Parse(ctx context.Context) (string, error) {
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
