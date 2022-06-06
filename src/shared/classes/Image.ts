export class Image {
  url: string
  height: number
  width: number

  static from(json){
    if (json === null) return null
    
    let image = new Image()
    
    image.url = json?.url
    image.height = json?.height
    image.width = json?.width
    
    return image
  }
}