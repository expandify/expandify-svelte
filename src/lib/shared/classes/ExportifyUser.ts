export class ExportifyUser {
  id: string
  access_token: string
  expires_in: number
  refresh_token: string
  scope: string
  token_type: string

  constructor(obj: any) {
    if (obj === null) throw new Error("Obj is empty")

    this.id = obj?.id
    this.access_token = obj?.access_token
    this.expires_in = obj?.expires_in
    this.refresh_token = obj?.refresh_token
    this.scope = obj?.scope
    this.token_type = obj?.token_type

  }
}