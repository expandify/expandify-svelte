export class ExplicitContent {
  filter_enabled: boolean
  filter_locked: boolean

  static from(json){
    if (json === null) return null

    let explicitContent = new ExplicitContent()

    explicitContent.filter_enabled = json?.filter_enabled
    explicitContent.filter_locked = json?.filter_locked

    return explicitContent
  }
}