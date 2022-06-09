export class ExternalUrls {
  spotify: string | null = null

  static from(json: any){
    if (json === null) return null

    let externalUrls = new ExternalUrls()

    externalUrls.spotify = json?.spotify
    
    return externalUrls
  }
}