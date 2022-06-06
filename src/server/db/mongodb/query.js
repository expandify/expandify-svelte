import {libraryCollection} from "./collections.js";
import {Library} from "../../../shared/classes/Library";

async function upsert(collection, item, query = {id: item.id}) {
  const update = {$set: item}
  const options = {upsert: true}

  try {
    return await collection.updateOne(query, update, options)
  } catch (e) {
    throw new Error("Error on upsert.")
  }
}

async function upsert_many(collection, items) {
  let bulkUpsertOptions = items.map((item) => createUpdateOption(item))
  try {
    return await collection.bulkWrite(bulkUpsertOptions)
  } catch (e) {
    throw new Error("Error on upsert.")
  }
}

function createUpdateOption(item, filter = {id: item.id}, update = {$set: item}, options = {upsert: true}) {
  return {
    "updateOne": {
      "filter": filter,
      "update": update,
      ...options
    }
  }
}

async function update(collection, newItems, filter) {
  let newValues = {$set: newItems};
  try {
    return await collection.updateOne(filter, newValues)
  } catch (err) {
    throw new Error("Error on update.")
  }
}

async function findAll(collection, filter) {
  try {
    return await collection.find(filter).toArray()
  } catch (err) {
    throw new Error("Error on findAll")
  }
}

async function getActiveLibrary(exportifyUser, libraryId) {
  if (libraryId === Library.Type.current) {
    return  await libraryCollection.findOne({"owner.item": exportifyUser.id, type: Library.Type.current})
  } else {
    return  await libraryCollection.findOne({owner: exportifyUser.id, _id: libraryId})
  }
}

async function getActiveLibraryItem(activeLibrary, collection, libraryItem) {

  if (!activeLibrary || activeLibrary[libraryItem].status === Library.Status.error) {
    return {status: 500}
  }
  if (activeLibrary[libraryItem].status === Library.Status.loading) {
    return {status: 202}
  }

  const items = await findAll(collection, {"id": {$in: activeLibrary[libraryItem].item || []}})
  return {
    status: 200,
    body: {
      items: items
    }
  }
}

export default {
  upsert,
  update,
  upsert_many,
  findAll,
  getActiveLibrary,
  getActiveLibraryItem
}