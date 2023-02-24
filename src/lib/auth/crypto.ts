import {Base64} from 'js-base64';

const CODE_CHARSET = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-._~';


/**
 * Generate random code verifier
 *
 * @param {number} [length]
 * @returns {string}
 */
export function generateRandomString(length: number = 50): string {
	const randomValues = window.crypto.getRandomValues(new Uint32Array(length));
	return Array.from(randomValues)
		.map((value) => value / 0x100000000) // Scale all values to [0, 1)
		.map((value) => CODE_CHARSET[Math.floor(value * CODE_CHARSET.length)])
		.join('');
}

/**
 * Create base64 encoded code challenge
 *
 * @param {string} codeVerifier
 * @returns {Promise<string>}
 */
export async function createCodeChallenge(codeVerifier: string): Promise<string> {
	const codeBuffer = new TextEncoder().encode(codeVerifier);
	const hashBuffer = await window.crypto.subtle.digest('SHA-256', codeBuffer);
	return Base64.fromUint8Array(new Uint8Array(hashBuffer), true);
}
