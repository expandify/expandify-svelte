export interface Paging<I> {
  href: string
  items: I[]
  limit: number
  next: string | null
  offset: number | null
  previous: string | null
  total: number

}