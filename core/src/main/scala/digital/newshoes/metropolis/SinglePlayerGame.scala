package digital.newshoes.metropolis

import digital.newshoes.metropolis.behavior.Simulate
import digital.newshoes.metropolis.event._
import digital.newshoes.metropolis.state._

import scala.collection.mutable

object SinglePlayerGame {

  def update(world: World, tick: Long) : Unit = {
    def routeEntityEvent(tee:ToEntityEvent) : Unit = {
      world.entities(tee.to).events += tee
    }

    val events = (for((id, e) <- world.entities) yield {
      e match {
        case entity:ConsciousEntity => updateConscious(world, tick, entity, id)
        case entity:StaticEntity => updateStatic(world, entity, id)
      }
    }).flatten // accumulate events and route them out

    val zoneEvents = new mutable.ArrayBuffer[ZoneEvent]()

    for(event <- events) {
      event match {
        case tee:ToEntityEvent => routeEntityEvent(tee)
        case ze:ZoneEvent => zoneEvents += ze
      }
    }

    // mutex over the zone events
  }

  def updateConscious(world: World, tick: Long, entity: ConsciousEntity, id: Long) : mutable.Queue[Event] = {
    val replyEvents = entity.events.flatMap { ev =>
      applyConsciousEvent(entity, ev, id)
    }

    val decision = Simulate.decide(world, tick, entity)

    applyStateTransitionToConscious(tick, entity, decision.transitions)

    // apply decision events
    val simulate = Simulate.simulate(world, tick, entity)

    applyStateTransitionToConscious(tick, entity, simulate.transitions)

    replyEvents ++ decision.events ++ simulate.events
  }

  def applyStateTransitionToConscious(tick: Long, entity: ConsciousEntity, transitions: Seq[StateTransition[Entity]]) : Seq[Event] = {
    for(transition <- transitions) {
      transition.applyTo(entity, tick)
    }
    Seq.empty
  }

  def applyConsciousEvent(entity:ConsciousEntity, ev:Event, id:Long) : Seq[Event] = {
    //    ev match {
    //      case PlaceItemSucceeded(itemType, _) =>
    //      case PlaceItemFailed(itemType, _) => entity.itemInHand = Some(itemType)
    //      case SuccessfulPickup(itemType, _) => entity.itemInHand = Some(itemType)
    //      case FailedPickup(entityId, _) =>
    //      case TakeDamage(damage, from, _) => {
    //        entity.health -= damage
    //        entity.alive = entity.health > 0
    //      }
    //    }
    Seq.empty
  }

  def updateStatic(world: World, entity: StaticEntity, id: Long):Seq[Event] = {
    Seq.empty
  }

  def applyStateTransitionToEntity(tick: Long, entity: Entity, transitions:Seq[StateTransition[Entity]]) : Seq[Event] = {
    for(transition <- transitions) {
      transition.applyTo(entity, tick)
    }
    Seq.empty
  }
}