export class Restrictions {
  reason: string | null = null

  static from(json: any){
    if (json === null) return null

    let restrictions = new Restrictions()
    restrictions.reason = json?.reason
    return restrictions
  }
}