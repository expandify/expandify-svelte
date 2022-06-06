export class Paging<Item> {
  href: string
  items: Item[]
  limit: number
  next: string | null
  offset: number
  previous: string | null
  total: number

  static from<Item>(json, parseFunc: (json: object) => Item){
    if (json === null) return null
    
    let paging = new Paging<Item>()

    paging.href = json?.href
    paging.limit = json?.limit
    paging.next = json?.next
    paging.offset = json?.offset
    paging.previous = json?.previous
    paging.total = json?.total
    paging.items = json?.items?.map(value => parseFunc(value))

    return paging
  }
}