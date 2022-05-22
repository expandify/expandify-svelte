import {buildLibraryStore, populatePagedStore} from "../../lib/client/library/store.js";

const albums = buildLibraryStore("albums")
populatePagedStore(albums, "/api/library/albums")

export {albums}