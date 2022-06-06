import driver from "./connection.js";

async function sessionQuery(query, parameters, transactionConfig) {
  let session = driver.session()
  session.run(query, parameters, transactionConfig)
  await session.close()
}


function addExportifyUser(exportifyUser) {

}