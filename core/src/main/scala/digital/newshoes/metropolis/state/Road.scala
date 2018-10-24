package digital.newshoes.metropolis.state

import upickle.default.{ReadWriter, macroRW}

case class Road(start: Long, end: Long, lots: Array[Long])

object Road {
  implicit def rw: ReadWriter[Road] = macroRW
}