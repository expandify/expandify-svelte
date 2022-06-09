import type ClientTemplate from "./ClientTemplate";
import ClientMongoDB from "./mongodb/ClientMongoDB";


const DBClient: ClientTemplate =  new ClientMongoDB()

export {DBClient}