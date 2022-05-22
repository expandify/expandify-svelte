
async function upsert(collection, item, query = {id: item.id}) {
  const update = { $set: item }
  const options = { upsert: true }

  try {
    return await collection.updateOne(query, update, options)
  }catch (e) {
    throw new Error("Error on upsert.")
  }
}


export default {
  upsert
}