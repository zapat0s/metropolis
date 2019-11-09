package digital.newshoes.metropolis.state

import upickle.default.{ReadWriter, macroRW}

case class Lot(road: Long, address: Long, building: Option[Long], owner: Option[Long])

object Lot {
  implicit val rw: ReadWriter[Lot] = macroRW
}
