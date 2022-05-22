import {writable, get} from "svelte/store";
import {browser} from "$app/env";
import {activateTheme, THEME_DEFAULTS} from "../lib/client/themes.js";


const themeStore = writable(THEME_DEFAULTS)

activateTheme()


// Whenever a change on the store happens, it will be persisted into localStorage
themeStore.subscribe(value => browser && localStorage.setItem("theme", JSON.stringify(value)))


export { themeStore }