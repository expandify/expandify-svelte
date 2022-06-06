export class Restrictions {
  reason: string

  static from(json){
    if (json === null) return null

    let restrictions = new Restrictions()
    restrictions.reason = json?.reason
    return restrictions
  }
}