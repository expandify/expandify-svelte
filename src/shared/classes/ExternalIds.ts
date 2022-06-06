export class ExternalIds {
  isrc: string | null = null
  ean: string | null = null
  upc: string | null = null

  static from(json: any){
    if (json === null) return null

    let externalIds = new ExternalIds()

    externalIds.isrc = json?.isrc
    externalIds.ean = json?.ean
    externalIds.upc = json?.upc

    return externalIds
  }
}