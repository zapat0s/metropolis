package digital.newshoes.metropolis.state

import upickle.default.{ReadWriter, macroRW}

case class Intersection(position: Vec2f, roads: List[Long])

object Intersection {
  implicit val rw: ReadWriter[Intersection] = macroRW
}