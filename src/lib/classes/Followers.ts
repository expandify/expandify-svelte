export class Followers {
  href!: string;
  total!: number;

  private constructor() {
  }

  public static fromFollowersObject(f: SpotifyApi.FollowersObject): Followers {
    const followers = new Followers();
    followers.href = f.href;
    followers.total = f.total;
    return followers;
  }
}