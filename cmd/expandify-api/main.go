package main

import (
	"expandify-api/cmd/internal"
	"expandify-api/pkg/api"
	"expandify-api/pkg/expandify"
	"expandify-api/pkg/expandify/authentication"
	"expandify-api/pkg/expandify/repository"
	"github.com/go-chi/jwtauth"
	"log"
	"net/http"
)

func main() {
	config := internal.LoadConfigs()
	repo := repository.New(repository.Config{
		StorageType:      config.StorageType,
		PostgresHost:     config.PostgresHost,
		PostgresUser:     config.PostgresUser,
		PostgresPassword: config.PostgresPassword,
		PostgresDbName:   config.PostgresDbName,
		PostgresPort:     config.PostgresPort,
		SQLiteFile:       config.SQLiteFile,
	})

	spotifyClient := expandify.NewSpotifyClient(config.ClientId, config.ClientSecret, config.RedirectUri)
	auth := authentication.New(spotifyClient, repo, &config.EncryptionKey)
	jwtAuth := jwtauth.New("HS256", config.JwtSecretAuth, nil)
	router := api.NewApi(auth, jwtAuth).Router()

	log.Println("Server is running on: " + config.Addr)
	err := http.ListenAndServe(config.Addr, router)
	log.Fatal(err)
}
