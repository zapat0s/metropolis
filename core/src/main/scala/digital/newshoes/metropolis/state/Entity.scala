package digital.newshoes.metropolis.state

import digital.newshoes.metropolis.event.ToEntityEvent

import scala.collection.mutable
import upickle.default.{ReadWriter => RW, macroRW}

sealed trait Entity {
  val id: Long
  val createdOn: Long
  val events = new mutable.Queue[ToEntityEvent]()
  var destroyedOn: Option[Long] = None
}

object Entity {
  implicit val rw: RW[Entity] = macroRW
}

sealed trait ConsciousEntity extends Entity

object ConsciousEntity {
  implicit val rw: RW[ConsciousEntity] = macroRW
}

case class Resident( id: Long,
                     bornOn: Long,
                     name: String,
                     var position: Vec2f,
                     var target: Option[Vec2f],
                     var residence: Long,
                     var employer: Long,
                     var health: Int = 100) extends ConsciousEntity {
  val createdOn: Long = bornOn
}

object Resident {
  implicit val rw: RW[Resident] = macroRW
}

case class Business( id: Long,
                     foundedOn: Long,
                     name: String,
                     businessType: Int,
                     var office: Long,
                     var employees: List[Long] = List.empty) extends ConsciousEntity {
  val createdOn: Long = foundedOn
}

object Business {
  implicit val rw: RW[Business] = macroRW
}

sealed trait StaticEntity extends Entity

object StaticEntity {
  implicit val rw: RW[StaticEntity] = macroRW
}

case class Building( id: Long,
                     builtOn: Long,
                     position: Vec2f,
                     components: List[BuildingComponent],
                     var occupant: Long,
                     var condition: Long = 100) extends StaticEntity {
  val createdOn: Long = builtOn
}

object Building {
  implicit val rw: RW[Building] = macroRW
}

case class Prop( id: Long,
                 builtOn: Long,
                 position: Vec2f,
                 components: List[BuildingComponent],
                 var condition: Long = 100) extends StaticEntity {
  val createdOn: Long = builtOn
}

object Prop {
  implicit val rw: RW[Prop] = macroRW
}

case class BuildingComponent(position: Vec2f, sprite: String)

object BuildingComponent {
  implicit val rw: RW[BuildingComponent] = macroRW
}

object Direction extends Enumeration {
  val North, South, East, West = Value
}