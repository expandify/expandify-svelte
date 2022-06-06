export class Image {
  url: string | null = null
  height: number | null = null
  width: number | null = null

  static from(json: any){
    if (json === null) return null
    
    let image = new Image()
    
    image.url = json?.url
    image.height = json?.height
    image.width = json?.width
    
    return image
  }
}