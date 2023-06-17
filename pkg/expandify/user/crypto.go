package user

import (
	"crypto/aes"
	"crypto/cipher"
	"crypto/rand"
	"encoding/base64"
	"errors"
	"io"
	mrand "math/rand"
	"strings"
)

const saltSeparator = '|'

func encrypt(text string, key *[32]byte) (ciphertext string, err error) {

	const letterBytes = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
	randSalt := make([]byte, 32)
	for i := range randSalt {
		randSalt[i] = letterBytes[mrand.Int63()%int64(len(letterBytes))]
	}

	plaintext := []byte(text + string(saltSeparator) + string(randSalt))

	block, err := aes.NewCipher(key[:])
	if err != nil {
		return "", err
	}

	gcm, err := cipher.NewGCM(block)
	if err != nil {
		return "", err
	}

	nonce := make([]byte, gcm.NonceSize())
	_, err = io.ReadFull(rand.Reader, nonce)
	if err != nil {
		return "", err
	}

	encrypted := gcm.Seal(nonce, nonce, plaintext, nil)

	return base64.StdEncoding.EncodeToString(encrypted), nil
}

func decrypt(text string, key *[32]byte) (plaintext string, err error) {

	ciphertext, err := base64.StdEncoding.DecodeString(text)
	if err != nil {
		return "", err
	}

	block, err := aes.NewCipher(key[:])
	if err != nil {
		return "", err
	}

	gcm, err := cipher.NewGCM(block)
	if err != nil {
		return "", err
	}

	if len(ciphertext) < gcm.NonceSize() {
		return "", errors.New("malformed ciphertext")
	}

	decrypted, err := gcm.Open(nil, ciphertext[:gcm.NonceSize()], ciphertext[gcm.NonceSize():], nil)
	if err != nil {
		return "", err
	}
	d := string(decrypted)
	return d[:strings.IndexByte(d, saltSeparator)], nil
}
