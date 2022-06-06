export class Cursor<Item> {
  href: string | null = null
  items: Item[] = []
  limit: number | null = null
  next: string | null = null
  cursors: { after: string | null } | null = null
  total: number | null = null

  static from<Item>(json: any, parseFunc: (json: any) => Item | null){
    if (json === null) return null
    
    let paging = new Cursor<Item>()

    paging.href = json?.href
    paging.limit = json?.limit
    paging.next = json?.next
    paging.cursors = json?.cursors
    paging.total = json?.total
    paging.items = json?.items?.map((value: any) => parseFunc(value))?.filter((x: any) => !!x)

    return paging
  }
}