# Exportify

Another Exportify Project.

## Project Plan

### Backend

- Umbrella Project
- Each umbrella app is a git submodule in this organisation
  - One git repo for each microservice
- Frontend might separate
- "https://github.com/jsncmgs1/spotify_ex" can be used.
  - All Library functions can be called with a `conn` or with `Spotify.Credentials`
  - Wenn called with a `conn`: `access_token` and `refresh_token` are safed in the cookies
  - Wenn called with `Credentials`, they have to be safed manually
    - **this is better**
    - Frontend and Backend communicate with a JWT. Only Backend knows the spotify access/refresh tokens
- API Project
  - Contains all API calls and delegates to the specific Service
  - Each call has to go through an Authentication-Service
  - JWT exists and is valid:
    1. Look in UserDatabase for access and refresh token
    2. If access_token is valid -> use it, else refresh it with the refresh token -> continue with request
    3. If refresh and access token are invalid -> continue as if JWT is invalid
  - JWT does not exist or is invalid
    1. 401 Unauthorized response
  - AuthenticationService.LoginWithSpotify
    3. Authorize and Authenticate with Spotify.
    4. Get User Spotify Details
    5. Safe Spotify Credentials and Details in User Database
    6. Create JWT from UserData
    7. 200 Succesfull with JWT in Response.
- Each Service (Album/Playlist/Artist/etc.):
  - Project with Database (no web api/no phoenix app)
  - Api calls the Servuces via Elixir OTP (cause they are in a umbrella project) (Api has Services as Dependencies)
  - Has functions to safe/get from Database and get from Spotify (and compare states)
- Safe Library
  - Backend Api call: Safe Library
  - Backend Api tells each Service to "Safe Library"
    - Each Service Response with the Ids of the Safed Items
    - Backend Api tells the LibraryService to Safe these Items as a Library for the current User
- Load Library
  - No general Loading.
  - Each Part (Album/Songs/Playlist/etc.) will be loaded, when the user clicks on its corresponding Page.
    - Loaded Data may be cached in the frontend
    - Update Button to refresh the cached Data

### Frontend Project

- When Page is loaded -> Check if JWT exists
  - If it exists check if it is valid (Backend Api call)
  - If valid -> continue as logged in user.
  - If invalid or it doesnt exists -> Homepage
- Homepage -> Login with Spotify
  1. Backend API call to Authorize and Authenticate with spotify.
  2. Recieve and Safe JWT
- On Requests:
  1. Send JWT
  2. When 401 -> Logout (Homepage) (Has to "login with spotify", to get a new JWT)
  3. When successfull -> use Reponse Data

### Databases

- User Database
  - Safes Spotify Credentials
- Library Database
  - Safes the "Owner"
  - The Libraries Playlists/Albums/Songs/etc.
- Song/Album/Playlist/etc. Databases safe "Spotify Response"
  - Ignore Relations.
  - Playlists may safe its Songs even though that Song is safed in the Songs Databse. 
    - Easier for the Start (And Storage is Cheap, since no Audio is safed)













