export class ExternalUrl implements SpotifyData {
  spotify!: string;

  private constructor() {
  }

  public static fromExternalUrlObject(euo: SpotifyApi.ExternalUrlObject): ExternalUrl {
    const externalUrl = new ExternalUrl();
    externalUrl.spotify = euo.spotify;
    return externalUrl;
  }
}