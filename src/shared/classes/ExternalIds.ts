export class ExternalIds {
  isrc: string
  ean: string
  upc: string

  static from(json){
    if (json === null) return null

    let externalIds = new ExternalIds()

    externalIds.isrc = json?.isrc
    externalIds.ean = json?.ean
    externalIds.upc = json?.upc

    return externalIds
  }
}