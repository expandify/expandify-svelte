package main

import (
	"expandify-api/cmd/internal"
	"expandify-api/pkg/api"
	"expandify-api/pkg/expandify"
	"log"
	"net/http"
)

func main() {
	config := internal.LoadConfigs()
	repoConnection := internal.NewRepositoryConnection(config)
	spotifyClient := expandify.NewSpotifyClient(config.ClientId, config.ClientSecret, config.RedirectUri, repoConnection.NewUserRepository())
	router := api.NewApi(spotifyClient, repoConnection.NewUserRepository(), repoConnection.NewSpotifyUserRepository(), &config.EncryptionKey, &config.JwtSecretAuth).Router()

	log.Println("Server is running on: " + config.Addr)
	err := http.ListenAndServe(config.Addr, router)
	log.Fatal(err)
}
