export class Followers {
  href: string
  total: number

  static from(json){
    if (json === null) return null
    
    let followers = new Followers()
    
    followers.href = json?.href
    followers.total = json?.total
    
    return followers
  }
}