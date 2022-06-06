export class Paging<Item> {
  href: string | null = null
  items: Item[] = []
  limit: number | null = null
  next: string | null = null
  offset: number | null = null
  previous: string | null = null
  total: number | null = null

  static from<Item>(json: any, parseFunc: (json: any) => Item | null){
    if (json === null) return null
    
    let paging = new Paging<Item>()

    paging.href = json?.href
    paging.limit = json?.limit
    paging.next = json?.next
    paging.offset = json?.offset
    paging.previous = json?.previous
    paging.total = json?.total
    paging.items = json?.items?.map((value: any) => parseFunc(value))?.filter((x: any) => !!x)

    return paging
  }
}