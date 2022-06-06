export class ExplicitContent {
  filter_enabled: boolean | null = null
  filter_locked: boolean | null = null

  static from(json: any){
    if (json === null) return null

    let explicitContent = new ExplicitContent()

    explicitContent.filter_enabled = json?.filter_enabled
    explicitContent.filter_locked = json?.filter_locked

    return explicitContent
  }
}