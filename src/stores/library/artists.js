import {buildLibraryStore, populateCursorStore, populatePagedStore} from "../../lib/client/library/store.js";


const artists = buildLibraryStore("artists")
populateCursorStore(artists, "/api/library/artists", data => data.artists)
export {artists}