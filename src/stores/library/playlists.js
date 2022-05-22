import {buildLibraryStore, populatePagedStore} from "../../lib/client/library/store.js";

const playlists = buildLibraryStore("playlists")
populatePagedStore(playlists, "/api/library/playlists", 10)

export {playlists}