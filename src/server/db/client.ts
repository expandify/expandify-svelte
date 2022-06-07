import type ClientTemplate from "./ClientTemplate.js";
import ClientMongoDB from "./mongodb/ClientMongoDB.js";


const DBClient: ClientTemplate =  new ClientMongoDB()

export {DBClient}