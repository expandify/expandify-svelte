# API

To start your Phoenix server:

  * Install dependencies with `mix deps.get`
  * Start Phoenix endpoint with `mix phx.server`

Now you can visit [`localhost:4000`](http://localhost:4000) from your browser.

Ready to run in production? Please [check our deployment guides](https://hexdocs.pm/phoenix/deployment.html).

# Api Resources

### Login

Call Type | Usage | Endpoint | Auth Required?
--------- | ----- | -------- | --------------
Get | Retrieve Spotify Login URL | /api/login | no
. | Callback After Login | /api/callback | no

### Current User Api References
Call Type | Usage | Endpoint | Auth Required?
--------- | ----- | -------- | --------------
Get | Get Current User Profile Info | /api/me | yes
Get | Get Current Library Tracks | /api/me/library/current/tracks | yes
Get | Get Current Library Artists| /api/me/library/current/artists | yes
Get | Get Current Library Albums| /api/me/library/current/albums | yes 
Get | Get Current Library Playlists| /api/me/library/current/playlists | yes
Post | Save Current Library | /api/me/library/current | yes

### Current User's Saved Librarys Api References
Call Type | Usage | Endpoint | Auth Required?
--------- | ----- | -------- | --------------
Get | Get Saved Library Tracks | /api/me/library/{id}/tracks | yes
Get | Get Saved Library Artists| /api/me/library/{id}/artists | yes
Get | Get Saved Library Albums| /api/me/library/{id}/albums | yes 
Get | Get Saved Library Playlists| /api/me/library/{id}/playlists | yes

### Albums Api References
Call Type | Usage | Endpoint | Auth Required?
--------- | ----- | -------- | --------------
Get | Get Information About An Album | /api/albums/{id} | yes
Get | Get Information About Multiple Albums | /api/albums | yes
Get | Get An Albums Tracks | /api/albums/{id}/tracks | yes

### Artists Api References
Call Type | Usage | Endpoint | Auth Required?
--------- | ----- | -------- | --------------
Get | Get Information About An Artist | /api/artists/{id} | yes
Get | Get Information About Multiple Artist | /api/artists | yes
Get | Get An Artists Albums | /api/artists/{id}/albums | yes 
Get | Get An Artists Related Artists | /api/artist/{id}/related-artists | yes
Get | Get An Artists Top Tracks |/api/artists/{id}/top-tracks |yes

### Playlist Api References
Call Type | Usage | Endpoint | Auth Required?
--------- | ----- | -------- | --------------
Get | Get A Playlist | /api/playlists/{id} | yes
Get | Get A Playlists Tracks | /api/playlist/{id}/tracks | yes

### Tracks Api References
Call Type | Usage | Endpoint | Auth Required?
--------- | ----- | -------- | --------------
Get | Get A Track | /api/tracks/{id} | yes
Get | Get Multiple Tracks | /api/tracks | yes
Get | Get Audio Features for a Track | /api/audio-features/{id} | yes
Get | Get Audio Features for multiple Tracks | /api/audio-features | yes
Get | Get Audio Analysis For A Track | /api/tracks/{id}/audio-analysis | yes

# Learn more

  * Official website: https://www.phoenixframework.org/
  * Guides: https://hexdocs.pm/phoenix/overview.html
  * Docs: https://hexdocs.pm/phoenix
  * Forum: https://elixirforum.com/c/phoenix-forum
  * Source: https://github.com/phoenixframework/phoenix
