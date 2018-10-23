package digital.newshoes.metropolis.state

import upickle.default.{macroRW, ReadWriter => RW}

case class World(size: Vec2f, lots: Array[Option[Long]], entities: Map[Long, Entity]) {

  def clamp(location: Vec2f): Vec2f = {
    val x = World.floor(location.x, 0, this.size.x)
    val y = World.floor(location.y, 0, this.size.y)
    Vec2f(x, y)
  }

  def isLotOccupied(x: Int, y: Int): Boolean = getLotAt(x, y).isDefined

  def getLotAt(x: Int, y: Int) : Option[Long] = {
    lots(lotPosition(x, y))
  }

  def setLotAt(x: Int, y: Int, l: Option[Long]) {
    lots(lotPosition(x, y)) = l
  }

  private def lotPosition(x: Int, y: Int) : Int = {
    (size.y * x + y).toInt
  }
}

object World {

  def floor(n: Float, min: Float, max: Float): Float = {
    if(n < min) {
      min
    } else if (n > max) {
      max
    } else {
      n
    }
  }

  def assignBorders(world: World, l: Option[Long]) {
    for {
      x <- 0 until world.size.x.toInt
    } {
      world.setLotAt(x, 0 , l)
      world.setLotAt(x, (world.size.y - 1.0).toInt, l)
    }

    for {
      y <- 0 until world.size.y.toInt
    } {
      world.setLotAt(0, y, l)
      world.setLotAt((world.size.x - 1).toInt, y, l)
    }
  }

  implicit def rw: RW[World] = macroRW
}


case class Vec2f(var x: Float, var y: Float) {
  def product: Float = x * y
  def :=(other: Vec2f) {
    x = other.x
    y = other.y
  }
}

object Vec2f {
  implicit def rw: RW[Vec2f] = macroRW
}