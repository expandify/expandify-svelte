const HOST_NAME = import.meta.env.VITE_HOST_NAME

function processData(data, callback, transform) {
  let transformedData
  try {
    transformedData = transform(data) || {}
  } catch (err) {
    transformedData = data
  }

  callback(transformedData)
  return transformedData
}

async function fetchItems(apiPath, options = {}) {

  let params = "?" + new URLSearchParams(options).toString()
  let path = `${HOST_NAME}${apiPath}${params}`

  const res = await fetch(path)
  return await res.json()
}


async function fetchPagedItems(apiPath,
                               callback = (_) => {},
                               transform = (data) => data,
                               limit = 50,
                               firstPage = null) {

  let data
  let offset = 0
  let next = null

  if (firstPage != null) {
    data = processData(firstPage, callback, transform)
    next = data.next
    offset = offset + data.limit
  }


  while (next != null) {
    let items = await fetchItems(apiPath, {offset: offset, limit: limit})
    data = processData(items, callback, transform)
    offset = offset + limit
    next = data.next
  }
}

async function fetchCursorItems(apiPath,
                                callback = (_) => {},
                                transform = (data) => data,
                                limit = 50,
                                firstPage = null) {

  let data
  let next = null
  let options = {limit: limit}

  if (firstPage != null) {
    data = processData(firstPage, callback, transform)
    limit = data.limit
    next = data.next
    options = {after: data.cursors?.after, limit: limit}
  }

  while (next != null) {
    const items = await fetchItems(apiPath, options)
    data = processData(items, callback, transform)

    options = {after: data.cursors?.after, limit: limit}
    next = data.next
  }
}

export {fetchItems, fetchPagedItems, fetchCursorItems}