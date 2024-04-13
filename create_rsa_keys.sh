#!/usr/bin/env bash

PRIVATE_SIGN_KEY=$(mktemp)
PRIVATE_ENCRYPT_KEY=$(mktemp)
RESOURCE_DIR="./src/main/resources"


openssl genrsa -out "${PRIVATE_SIGN_KEY}" 2048
openssl rsa -pubout -in "${PRIVATE_SIGN_KEY}" -out "${RESOURCE_DIR}"/public_verify.pem
# convert private key to pkcs8 format in order to import it from Java
openssl pkcs8 -topk8 -in "${PRIVATE_SIGN_KEY}" -inform pem -out "${RESOURCE_DIR}"/private_sign.pem -outform pem -nocrypt


openssl genrsa -out "${PRIVATE_ENCRYPT_KEY}" 2048
openssl rsa -pubout -in "${PRIVATE_ENCRYPT_KEY}" -out "${RESOURCE_DIR}"/public_encrypt.pem
# convert private key to pkcs8 format in order to import it from Java
openssl pkcs8 -topk8 -in "${PRIVATE_ENCRYPT_KEY}" -inform pem -out "${RESOURCE_DIR}"/private_decrypt.pem -outform pem -nocrypt