package digital.newshoes.metropolis.state

trait StateTransition[T] {
  def applyTo(entity: T, tick: Long)
}

trait InstantStateTransition[T] extends StateTransition[T] // decide can only return instant state transitions

case object Death extends InstantStateTransition[Entity] {
  def applyTo(entity: Entity, tick: Long) {
    entity.destroyedOn = Some(tick)
  }
}

case class SetTarget(target: Vec2f) extends InstantStateTransition[Entity] {
  def applyTo(entity: Entity, tick: Long): Unit = {
    entity match {
      case entity: Resident => entity.target = Some(target)
      case _ =>
    }
  }
}

trait SimulatedStateTransition[T] extends StateTransition[T] // only update is allowed to return simulated state transitions (it can also return instants)

case class ChangePosition(position: Vec2f) extends SimulatedStateTransition[Entity] {
  def applyTo(entity: Entity, tick: Long) {
    entity match {
      case entity: Resident => entity.position := position
      case _ =>
    }
  }
}