package digital.newshoes.metropolis.state

import upickle.default.{macroRW, ReadWriter => RW}

case class World(roads: Map[Long, Road], intersections: Map[Long, Intersection], lots: Map[Long, Lot],  entities: Map[Long, Entity]) {
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

  implicit def rw: RW[World] = macroRW
}