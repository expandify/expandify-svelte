export class ExternalId implements SpotifyData {
  isrc?: string;
  ean?: string;
  upc?: string;

  private constructor() {
  }

  public static fromExternalIdObject(e: SpotifyApi.ExternalIdObject): ExternalId {
    const externalId = new ExternalId();
    externalId.isrc = e.isrc;
    externalId.ean = e.ean;
    externalId.upc = e.upc;
    return externalId;
  }
}