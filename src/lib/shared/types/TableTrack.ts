export interface TableTrack {
  name: string
  artists: { id: string, name: string }[]
  album: { id: string, name: string }
  added_at: string
  duration: string
  image: string
  id: string
}