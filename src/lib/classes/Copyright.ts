export class Copyright implements SpotifyData {
  text!: string;
  type!: 'C' | 'P';

  private constructor() {
  }

  public static fromCopyrightObject(co: SpotifyApi.CopyrightObject): Copyright {
    const copyright = new Copyright();
    copyright.text = co.text;
    copyright.type = co.type;
    return copyright;
  }
}