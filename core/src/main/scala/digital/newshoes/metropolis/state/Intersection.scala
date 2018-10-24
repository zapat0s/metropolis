package digital.newshoes.metropolis.state

import upickle.default.{ReadWriter, macroRW}

case class Intersection(position: Vec2f, roads: List[Long])

object Intersection {
  implicit def rw: ReadWriter[Intersection] = macroRW
}