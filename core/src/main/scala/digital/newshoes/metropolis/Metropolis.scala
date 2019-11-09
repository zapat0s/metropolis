package digital.newshoes.metropolis

import com.badlogic.gdx.{ApplicationAdapter, Gdx}
import digital.newshoes.metropolis.rendering.LibGDXRenderer
import digital.newshoes.metropolis.serialization.JsonWorldSerializer
import digital.newshoes.metropolis.state._

class Metropolis extends ApplicationAdapter {
  //  private val world = JsonWorldSerializer.load("testWorld.json")
  private val world: World = {
    val entities = Map(
      0L -> Resident(0L, 0, "Bobbie", Vec2f(1, 1), Some(Vec2f(1, 1)), 1, 2),
      1L -> Building(1L, 0, Vec2f(1, 1), List(BuildingComponent(Vec2f(0, 0), "wall"), BuildingComponent(Vec2f(0, 2), "roof")), 0),
      2L -> Business(2L, 0, "Acme", 0, 3, List(0)),
      3L -> Building(3L, 0, Vec2f(6, 6), List(BuildingComponent(Vec2f(-2, 0), "wall"), BuildingComponent(Vec2f(0, 0), "wall"), BuildingComponent(Vec2f(-2, 2), "roof"), BuildingComponent(Vec2f(0, 2), "roof")), 0)
    )
    val roads = Map(
      0L -> Road(0, 1, Array.empty),
      1L -> Road(1, 2, Array.empty)
    )
    val intersections = Map(
      0L -> Intersection(Vec2f(0, 6), List(0)),
      1L -> Intersection(Vec2f(0, 0), List(0, 1)),
      2L -> Intersection(Vec2f(6, 0), List(1))
    )
    World(roads, intersections, Map(), entities)
  }

    JsonWorldSerializer.save(world, "testWorld.json")

  private var tick = 0
  private var elapsedRealWorldTime = 0.0f

  override def create(): Unit = {
    LibGDXRenderer.initialize()
  }

  override def render(): Unit = {
    elapsedRealWorldTime += Gdx.graphics.getDeltaTime
    if (elapsedRealWorldTime >= 1) {
      tick += 1
      elapsedRealWorldTime = 0
    }

    SinglePlayerGame.update(world, tick)

    LibGDXRenderer.render(world, tick)
  }

  override def dispose(): Unit = {
    LibGDXRenderer.cleanup()
  }
}
