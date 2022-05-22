const HOST_NAME = import.meta.env.VITE_HOST_NAME

async function fetchItems(apiPath, callback = (_) => {}, options = {}, transform = (data) => data) {

  let params = "?" + new URLSearchParams(options).toString()
  let path = `${HOST_NAME}${apiPath}${params}`

  const res = await fetch(path)
  const data = await res.json()

  let transformedData

  try {
    transformedData = transform(data) || {}
  } catch (err) {
    transformedData = data
  }
  callback(transformedData)
  return transformedData
}

async function fetchPagedItems(apiPath, callback = (_) => {},  limit = 50) {

  let offset = 0
  let next = null
  do {
    const data = await fetchItems(apiPath, callback, {offset: offset, limit: limit})

    offset = offset + limit
    next = data.next
  } while (next != null)
}

async function fetchCursorItems(apiPath, callback = (_) => {}, transform = (data) => data,  limit = 50) {

  let next = null
  let options = {limit: limit}
  do {
    const data = await fetchItems(apiPath, callback, options, transform)
    options = {after: data.cursors?.after, limit: limit}
    next = data.next
  } while (next != null)
}

export {fetchItems, fetchPagedItems, fetchCursorItems}