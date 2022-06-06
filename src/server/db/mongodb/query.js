
async function upsert(collection, item, query = {id: item.id}) {
  const update = { $set: item }
  const options = { upsert: true }

  try {
    return await collection.updateOne(query, update, options)
  }catch (e) {
    throw new Error("Error on upsert.")
  }
}

async function update(collection, newItems, query) {
  let newValues = { $set: newItems };
  try {
    return await collection.updateOne(query, newValues)
  } catch (err) {
    throw new Error("Error on update.")
  }
}

export default {
  upsert,
  update
}