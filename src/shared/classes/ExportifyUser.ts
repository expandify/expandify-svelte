export class ExportifyUser {
  id: string | null = null
  access_token: string | null = null
  expires_in: number | null = null
  refresh_token: string | null = null
  scope: string | null = null
  token_type: string | null = null

  static from(json: any){
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