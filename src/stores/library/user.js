import {buildLibraryStore, populateStore, STORE_STATUS} from "../../lib/client/library/store.js";

const USER_DEFAULTS = {
  user: {},
  status: STORE_STATUS.INIT
}

const user = buildLibraryStore("user", USER_DEFAULTS)

populateStore(user, "/api/library/user", data => {
  user.set({user: data, status: STORE_STATUS.FINISHED})
})


export {user}
