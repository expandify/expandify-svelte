import * as Neo4j from 'neo4j-driver'

const user = process.env.VITE_NEO4J_USERNAME || import.meta.env.VITE_NEO4J_USERNAME
const password = process.env.VITE_NEO4J_PASSWORD || import.meta.env.VITE_NEO4J_PASSWORD
const uri = process.env.VITE_NEO4J_URI || import.meta.env.VITE_NEO4J_URI


let driver = Neo4j.driver(uri, Neo4j.auth.basic(user, password))

export default driver