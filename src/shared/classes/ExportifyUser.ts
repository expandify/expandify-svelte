export class ExportifyUser {
  id: string
  access_token: string
  expires_in: number
  refresh_token: string
  scope: string
  token_type: string

  static from(json){
    if (json === null) return null

    let exportifyUser = new ExportifyUser()

    exportifyUser.id = json?.id
    exportifyUser.access_token = json?.access_token
    exportifyUser.expires_in = json?.expires_in
    exportifyUser.refresh_token = json?.refresh_token
    exportifyUser.scope = json?.scope
    exportifyUser.token_type = json?.token_type

    return exportifyUser
  }
}