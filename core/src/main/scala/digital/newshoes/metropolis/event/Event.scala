package digital.newshoes.metropolis.event

import digital.newshoes.metropolis.state._

trait Event

trait FromEntityEvent extends Event {
  def from: Int
} // all events are routed
trait ToEntityEvent extends Event { def to: Int }

// doesn't cause a state transition, just a flag to hold on to it rather than mutate
// mainly used for failures, or any event that doesn't transition state

trait ZoneEvent extends Event // routed to event

case class DecisionOutput(events: Seq[Event], transitions: Seq[InstantStateTransition[Entity]])
case class SimulationOutput(events: Seq[Event], transitions: Seq[StateTransition[Entity]])


// ROUTED EVENTS
case class PlaceItemAt(at: Vec2i, itemType: Int, from: Int) extends FromEntityEvent with ZoneEvent {
  def failure = PlaceItemFailed(itemType, from)
}
case class PlaceItemFailed(itemType: Int, to: Int) extends ToEntityEvent
case class PlaceItemSucceeded(itemType: Int, to: Int) extends ToEntityEvent

case class RequestPath(fromPosition: Vec2i, toPosition: Vec2i, from: Int) extends FromEntityEvent {
  def failure = PathRequestFailed(from)
}

case class PathRequestSuccessful(path: Seq[Int], to: Int) extends ToEntityEvent
case class PathRequestFailed(to :Int) extends ToEntityEvent

case class PickupItem(to: Int, from: Int) extends FromEntityEvent with ToEntityEvent {
  def failure = FailedPickup(to, from)
}
case class SuccessfulPickup(itemType: Int, to: Int) extends ToEntityEvent
case class FailedPickup(from: Int, to: Int) extends ToEntityEvent

case class DropItem(itemType:Int, from:Int) extends FromEntityEvent with ZoneEvent // unsure if this is needed

case class TakeDamage(damage:Int, from:Int, to:Int) extends FromEntityEvent with ToEntityEvent
