import { makeRequest } from "../request";

export async function laodUser() {  
  return await makeRequest((api) => api.getMe());  
}