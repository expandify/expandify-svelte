import {writable, get} from "svelte/store";
import {browser} from "$app/env";

let THEME_DEFAULTS = {
  darkMode: true,
  accent: "orange"
}

let SUPPORTED_ACCENTS = [
  "orange", "red", "blue", "green", "pink"
]


const themeStore = writable(THEME_DEFAULTS)

if (browser) {
  // If we are in a browser, check if the localStorage already has items
  const localThemeStore = localStorage.getItem("theme")

  if (localThemeStore) {
    try {
      // Needs to be parsed, since local storage can only handle strings
      themeStore.set(JSON.parse(localThemeStore))
    } catch (err) {
      themeStore.set(THEME_DEFAULTS)
    }
  }
}

// Whenever a change on the store happens, it will be persisted into localStorage
themeStore.subscribe(value => browser && localStorage.setItem("theme", JSON.stringify(value)))

function setTheme() {
  if (browser) {
    const store = get(themeStore)
    const darkMode = store.darkMode
    const accent = store.accent

    setDarkMode(darkMode)

    setAccent(accent)
  }
}

function setDarkMode(darkMode) {
  if (browser) {
    const body = document.body

    if (darkMode) {
      body.classList.remove("light-mode")
      body.classList.add("dark-mode")
    } else {
      body.classList.remove("dark-mode")
      body.classList.add("light-mode")
    }
    themeStore.update(current => ({darkMode: darkMode, accent: current.accent}))
  }
}

function setAccent(accent) {
  if (browser) {
    const body = document.body

    const newAccent = SUPPORTED_ACCENTS.includes(accent) ? "accent-" + accent : "accent-" + SUPPORTED_ACCENTS[0]
    const oldAccents = []
    body.classList.forEach(value => {
      if (value.startsWith("accent-")) {
        oldAccents.push(value)
      }
    })

    oldAccents.forEach(value => body.classList.remove(value))
    body.classList.add(newAccent)
  }
}

export { themeStore, setTheme, setDarkMode, setAccent, SUPPORTED_ACCENTS }