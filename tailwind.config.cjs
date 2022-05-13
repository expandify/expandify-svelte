const config = {
  content: ["./src/**/*.{html,js,svelte,ts}"],

  theme: {
    extend: {},
  },

  plugins: [require("daisyui")],

  daisyui: {
    themes: ["light", "dark", "night"],
    darkTheme: "",
  }
};

module.exports = config;
