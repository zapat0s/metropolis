package digital.newshoes.metropolis.state

import scala.collection.mutable
import digital.newshoes.metropolis.event.ToEntityEvent

abstract class Entity(createdOn: Long, var destroyedOn: Option[Long] = None) {
  val events = new mutable.Queue[ToEntityEvent]()
}

abstract class ConsciousEntity(createdOn: Long) extends Entity(createdOn)

case class Resident(
                     bornOn: Long,
                     name: String,
                     var position: Vec2f,
                     var target: Option[Vec2f],
                     var residence: Long,
                     var employer: Long,
                     var health: Int = 100) extends ConsciousEntity(bornOn)

case class Business(
                     foundedOn: Long,
                     name: String,
                     businessType: Int,
                     var office: Long,
                     var employees: List[Long] = List.empty) extends ConsciousEntity(foundedOn)


abstract class StaticEntity(createdOn: Long) extends Entity(createdOn)

case class Building(
                     builtOn: Long,
                     position: Vec2f,
                     rotation: Direction.Value,
                     components: List[BuildingComponent],
                     var occupant: Long,
                     var condition: Long = 100) extends StaticEntity(builtOn)

case class BuildingComponent(position: Vec2f, sprite: String)

object Direction extends Enumeration {
  val North, South, East, West = Value
}