package main

import (
	"expandify-api/cmd/internal"
	"expandify-api/pkg/api"
	"expandify-api/pkg/expandify"
	"expandify-api/pkg/expandify/repository"
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
	router := api.NewApi(spotifyClient, repo, &config.EncryptionKey, &config.JwtSecretAuth).Router()

	log.Println("Server is running on: " + config.Addr)
	err := http.ListenAndServe(config.Addr, router)
	log.Fatal(err)
}
