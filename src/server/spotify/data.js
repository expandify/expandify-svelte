import Query from "../db/mongodb/query.js";
import {libraryCollection} from "../db/mongodb/collections.js";
import {Library, LibraryItem} from "../../shared/classes/Library";

async function saveLibraryItems(collection, libraryId, libraryItem, newItems) {
  try {
    await Query.upsert_many(collection, newItems)
    return await updateLibraryItem(libraryId, libraryItem, Library.Status.ready, newItems.map(value => value.id))
  } catch (err) {
    await updateLibraryItem(libraryId, libraryItem, Library.Status.error, [])
    throw new Error("Error updating Library.")
  }
}

async function updateLibraryItem(libraryId, libraryItem, status, newItem) {
  try {
    let currentDate = new Date().toString()
    let library = {}
    library[libraryItem] = new LibraryItem(status, currentDate, newItem)

    let filter = {_id: libraryId}
    return await Query.upsert(libraryCollection, library, filter)
  } catch (err) {
    throw new Error("Error updating Library.")
  }
}


export {saveLibraryItems, updateLibraryItem}