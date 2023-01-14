export class SpotifyImage {
  height?: number;
  url!: string;
  width?: number;

  private constructor() {
  }

  public static fromImageObject(image: SpotifyApi.ImageObject): SpotifyImage {
    const spotifyImage = new SpotifyImage();
    spotifyImage.height = image.height;
    spotifyImage.url = image.url;
    spotifyImage.width = image.width;
    return spotifyImage;
  }
}