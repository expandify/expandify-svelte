# API

To start your Phoenix server:

  * Install dependencies with `mix deps.get`
  * Start Phoenix endpoint with `mix phx.server`

Now you can visit [`localhost:4000`](http://localhost:4000) from your browser.

Ready to run in production? Please [check our deployment guides](https://hexdocs.pm/phoenix/deployment.html).

# Api Resources

Call Type | Resource | Endpoint
--------- | -------- | --------
Get | Current Library Songs| /api/library/current/songs
Get | Current Library Artists| /api/library/current/artists
Get | Current Library Albums| /api/library/current/albums
Get | Current Library Playlists| /api/library/current/playlists
Get | Any Library Songs, etc. | /api/libraray/:lib_id/{songs, artists, albums, playlists}
Post | Safe Current Libraray | /api/library/current
Get | Retrieve Spotify Login URL | /api/login
Get | Song by Id | /api/song/:id
Get | Album by Id | /api/album/:id
Get | Artist by Id | /api/artist/:id

## Learn more

  * Official website: https://www.phoenixframework.org/
  * Guides: https://hexdocs.pm/phoenix/overview.html
  * Docs: https://hexdocs.pm/phoenix
  * Forum: https://elixirforum.com/c/phoenix-forum
  * Source: https://github.com/phoenixframework/phoenix
