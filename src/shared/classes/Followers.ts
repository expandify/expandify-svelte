export class Followers {
  href: string | null = null
  total: number | null = null

  static from(json: any){
    if (json === null) return null
    
    let followers = new Followers()
    
    followers.href = json?.href
    followers.total = json?.total
    
    return followers
  }
}