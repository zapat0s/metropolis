package digital.newshoes.metropolis.state

import collection.mutable

case class Lot(var occupant:Option[StaticEntity], var glyph:Byte)

class World(val size: Vec2i) {
  def clamp(location: Vec2i): Vec2i = {
    val x = World.floor(location.x, 0, this.size.x)
    val y = World.floor(location.y, 0, this.size.y)
    Vec2i(x, y)
  }

  val lots = new Array[Lot](size.product)
  val entities = new mutable.HashMap[Long, Entity]()

  def isLotOccupied(x: Int, y: Int): Boolean = getLotAt(x, y).occupant.isDefined

  def getLotAt(x: Int, y: Int) : Lot = {
    lots(lotPosition(x, y))
  }

  def setLotAt(x: Int, y: Int, l:Lot) {
    lots(lotPosition(x, y)) = l
  }

  private def lotPosition(x: Int, y: Int) : Int = {
    size.y * x + y
  }
}

object World {

  def floor(n: Int, min: Int, max: Int): Int = {
    if(n < min) {
      min
    } else if (n > max) {
      max
    } else {
      n
    }
  }

  def assignBorders(world: World, l: Lot) {
    for {
      x <- 0 until world.size.x
    } {
      world.setLotAt(x, 0 , l)
      world.setLotAt(x, world.size.y - 1, l)
    }

    for {
      y <- 0 until world.size.y
    } {
      world.setLotAt(0, y, l)
      world.setLotAt(world.size.x - 1, y, l)
    }
  }
}



case class Vec2i(var x: Int, var y: Int) {
  def product = x * y
  def :=(other: Vec2i) {
    x = other.x
    y = other.y
  }
}