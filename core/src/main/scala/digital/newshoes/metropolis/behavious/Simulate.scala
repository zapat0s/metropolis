package digital.newshoes.metropolis.behavious

import digital.newshoes.metropolis.event._
import digital.newshoes.metropolis.state._

object Simulate {
  // occurs at an instant, generally AI or player controls
  def decide(world: World, tick: Long, entity: ConsciousEntity): DecisionOutput = {
    val hourOfDay = tick % 24

    var stateTransitions = List[InstantStateTransition[Entity]]()
    entity match {
      case r: Resident =>
        if(hourOfDay > 8 && hourOfDay < 16) {
          val employer = world.entities(r.employer).asInstanceOf[Business]
          val workplace = world.entities(employer.office).asInstanceOf[Building]
          if (!workplace.lots.contains(r.position)) {
            stateTransitions = SetTarget(workplace.lots.head) :: stateTransitions
          }
        } else {
          val home = world.entities(r.residence).asInstanceOf[Building]
          stateTransitions = SetTarget(home.lots.head) :: stateTransitions
        }
      case _ =>
    }

    DecisionOutput(Seq.empty, stateTransitions)
  }

  // occurs over a period of time
  def simulate(world: World, tick: Long, entity: Entity) : SimulationOutput = {
    var stateTransitions = List[StateTransition[Entity]]()

    entity match {
      case r: Resident =>
        if(r.target.isDefined) {
          val dirX = r.position.x + World.floor(r.target.get.x - r.position.x, -1, 1)
          val dirY = r.position.y + World.floor(r.target.get.y - r.position.y, -1, 1)
          stateTransitions = ChangePosition(world.clamp(Vec2i(dirX, dirY))) :: stateTransitions
        }
      case _ =>
    }

    SimulationOutput(Seq.empty, stateTransitions)
  }
}