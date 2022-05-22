import {buildLibraryStore, populatePagedStore} from "../../lib/client/library/store.js";

const songs = buildLibraryStore("songs")
populatePagedStore(songs, "/api/library/songs")

export {songs}