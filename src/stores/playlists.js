import {buildStore, fetchPagedItems} from "./builder.js";

export const playlists = buildStore("playlists")

fetchPagedItems(playlists, "/library/playlists")