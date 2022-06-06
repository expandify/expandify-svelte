export class ExternalUrls {
  spotify: string

  static from(json){
    if (json === null) return null

    let externalUrls = new ExternalUrls()

    externalUrls.spotify = json?.spotify
    
    return externalUrls
  }
}