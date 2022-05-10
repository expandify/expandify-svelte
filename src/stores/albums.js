import {buildStore, fetchPagedItems} from "./builder.js";

export const albums = buildStore("albums")

fetchPagedItems(albums, "/library/albums")