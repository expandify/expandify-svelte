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
	repo := internal.NewRepository(config)
	spotifyClient := expandify.NewSpotifyClient(config.ClientId, config.ClientSecret, config.RedirectUri, repo)
	router := api.NewApi(spotifyClient, repo, &config.EncryptionKey, &config.JwtSecretAuth).Router()

	log.Println("Server is running on: " + config.Addr)
	err := http.ListenAndServe(config.Addr, router)
	log.Fatal(err)
}
