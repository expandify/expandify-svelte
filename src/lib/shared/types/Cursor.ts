export interface Cursor<I> {
  href: string
  items: I[]
  limit: number
  next: string | null
  cursors: { after: string | null }
  total: number

}