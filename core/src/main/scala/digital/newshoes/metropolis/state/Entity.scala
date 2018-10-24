package digital.newshoes.metropolis.state

import digital.newshoes.metropolis.event.ToEntityEvent
import upickle.default.{ReadWriter, macroRW}

import scala.collection.mutable

sealed trait Entity {
  val id: Long
  val createdOn: Long
  var destroyedOn: Option[Long] = None
  val events = new mutable.Queue[ToEntityEvent]()
}

object Entity{
  implicit def rw: ReadWriter[Entity] = ReadWriter.merge(ConsciousEntity.rw, StaticEntity.rw)
}

sealed trait ConsciousEntity extends Entity

object ConsciousEntity{
  implicit def rw: ReadWriter[ConsciousEntity] = ReadWriter.merge(Resident.rw, Business.rw)
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
  implicit def rw: ReadWriter[Resident] = macroRW
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
  implicit def rw: ReadWriter[Business] = macroRW
}


sealed trait StaticEntity extends Entity

object StaticEntity{
  implicit def rw: ReadWriter[StaticEntity] = ReadWriter.merge(Building.rw, Prop.rw)
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
  implicit def rw: ReadWriter[Building] = macroRW
}

case class Prop( id: Long,
                 builtOn: Long,
                 position: Vec2f,
                 components: List[BuildingComponent],
                 var condition: Long = 100) extends StaticEntity {
  val createdOn: Long = builtOn
}

object Prop {
  implicit def rw: ReadWriter[Prop] = macroRW
}

case class BuildingComponent(position: Vec2f, sprite: String)

object BuildingComponent {
  implicit def rw: ReadWriter[BuildingComponent] = macroRW
}

object Direction extends Enumeration {
  val North, South, East, West = Value
}