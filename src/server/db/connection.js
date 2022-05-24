import { MongoClient } from 'mongodb';

const development = import.meta.env.DEV

const uri = process.env.VITE_MONGODB_URI || import.meta.env.VITE_MONGODB_URI

const options = {}

let client
let clientPromise

if (!uri) {
  throw new Error('Please add VITE_MONGODB_URI to .env')
}

if (development) {
  // In development mode, use a global variable so that the value
  // is preserved across module reloads caused by HMR (Hot Module Replacement).
  if (!global._mongoClientPromise) {
    client = new MongoClient(uri, options)
    global._mongoClientPromise = client.connect()
  }
  clientPromise = global._mongoClientPromise
} else {
  // In production mode, it's best to not use a global variable.
  client = new MongoClient(uri, options)
  clientPromise = client.connect()
}


export default clientPromise