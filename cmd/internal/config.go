package internal

import (
	"log"
	"os"
	"strings"
)

type Config struct {
	ClientId     string
	ClientSecret string
	RedirectUri  string

	StorageType string

	PostgresHost     string
	PostgresUser     string
	PostgresPassword string
	PostgresDbName   string
	PostgresPort     string

	SQLiteFile string

	Addr          string
	EncryptionKey [32]byte
	JwtSecretAuth []byte
}

func LoadConfigs() *Config {

	clientId := loadMandatoryEnv("EXPANDIFY_SPOTIFY_ID")
	clientSecret := loadMandatoryEnv("EXPANDIFY_SPOTIFY_SECRET")
	redirectUri := loadMandatoryEnv("EXPANDIFY_SPOTIFY_REDIRECT_URI")
	storageType := loadEnvWithFallback("EXPANDIFY_STORAGE_TYPE", "sqlite")
	var postgresHost string
	var postgresUser string
	var postgresPassword string
	var postgresDbName string
	var postgresPort string
	var sqliteFile string

	switch strings.ToLower(storageType) {
	case TypePostgres:
		postgresHost = loadEnvWithFallback("EXPANDIFY_DB_HOST", "localhost")
		postgresUser = loadEnvWithFallback("EXPANDIFY_DB_USER", "expandify")
		postgresPassword = loadEnvWithFallback("EXPANDIFY_DB_PASSWORD", "expandify")
		postgresDbName = loadEnvWithFallback("EXPANDIFY_DB_NAME", "expandify")
		postgresPort = loadEnvWithFallback("EXPANDIFY_DB_PORT", "5432")
	case TypeSQLite:
		sqliteFile = loadEnvWithFallback("EXPANDIFY_SQLITE_FILE", "sqlite.db")
	}

	addr := loadEnvWithFallback("EXPANDIFY_SERVER_ADDR", "5432")
	encryptionKey := loadMandatory32ByteEnv("EXPANDIFY_ENCRYPTION_KEY")
	jwtSecretAuth := loadMandatoryEnv("EXPANDIFY_JWT_SECRET")

	return &Config{
		ClientId:         clientId,
		ClientSecret:     clientSecret,
		RedirectUri:      redirectUri,
		StorageType:      storageType,
		PostgresHost:     postgresHost,
		PostgresUser:     postgresUser,
		PostgresPassword: postgresPassword,
		PostgresDbName:   postgresDbName,
		PostgresPort:     postgresPort,
		SQLiteFile:       sqliteFile,
		Addr:             addr,
		EncryptionKey:    encryptionKey,
		JwtSecretAuth:    []byte(jwtSecretAuth),
	}
}

func loadEnvWithFallback(key string, fallback string) string {
	val, ok := os.LookupEnv(key)
	if !ok {
		log.Printf("%s not set, falling back to %s", key, fallback)
		val = fallback
	}
	return val
}

func loadMandatoryEnv(key string) string {
	val, ok := os.LookupEnv(key)
	if !ok {
		log.Fatalf("%s not set.", key)
	}
	return val
}

func loadMandatory32ByteEnv(key string) [32]byte {
	key = loadMandatoryEnv(key)
	if len(key) != 32 {
		log.Fatalf("%s needs to be 32 bytes long.", key)
	}
	var bytes [32]byte
	copy(bytes[:], key)
	return bytes
}
