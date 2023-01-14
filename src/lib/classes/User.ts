import { ExternalUrl } from "./ExternalUrl";
import { Followers } from "./Followers";
import { SpotifyImage } from "./SpotifyImage";

export class User implements SpotifyData {
  // User Private
  birthdate?: string;
  country?: string;
  email?: string;
  product?: string;

  // User Public
  display_name?: string;
  external_urls!: ExternalUrl;
  followers?: Followers;
  href!: string;
  id!: string;
  images?: SpotifyImage[];
  type!: 'user';
  uri!: string;

  private constructor() {
  }

  public static fromPrivateUser(u: SpotifyApi.UserObjectPrivate): User {
    const user = User.fromPublicUser(u);
    user.birthdate = u.birthdate;
    user.country = u.country;
    user.email = u.email;
    user.product = u.product;
    return user;
  }

  public static fromPublicUser(u: SpotifyApi.UserObjectPublic): User {
    const user = new User();
    user.display_name = u.display_name;
    user.external_urls = ExternalUrl.fromExternalUrlObject(u.external_urls);
    user.followers = u.followers && Followers.fromFollowersObject(u.followers);
    user.href = u.href;
    user.id = u.id;
    user.images = u.images?.map(i => SpotifyImage.fromImageObject(i));
    user.type = u.type;
    user.uri = u.uri;
    return user;
  }
}