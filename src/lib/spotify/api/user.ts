import { updateStatus, setUpdatedNow, clearUser, setUser } from "$lib/stores/library/user"
import { StoreState } from "$lib/stores/types";
import { userPrivate } from "../converter";
import { makeRequest } from "../request";

export async function laodUser() {
  clearUser();
  updateStatus(StoreState.Loading);

  try {  
    const user = userPrivate(await makeRequest((api) => api.getMe()));  

    setUser(user);
  } catch (error) {
    updateStatus(StoreState.Error);
  }

  setUpdatedNow();
  updateStatus(StoreState.Ready);
}