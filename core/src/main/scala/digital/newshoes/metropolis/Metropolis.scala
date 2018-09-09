package digital.newshoes.metropolis

import com.badlogic.gdx.{ApplicationAdapter, Gdx}
import digital.newshoes.metropolis.render.LibGDXRender
import digital.newshoes.metropolis.state._

class Metropolis extends ApplicationAdapter {

  private val world = {
    val w = new World(size = Vec2f(16, 16))
    w.entities += 0L -> Resident(0,"Bobbie", Vec2f(1, 1), Some(Vec2f(1, 1)), 1, 2)
    w.entities += 1L -> Building(0, Vec2f(1, 1), Direction.North, List(BuildingComponent(Vec2f(0, 0), "wall"), BuildingComponent(Vec2f(0, 2), "roof")), 0)
    w.entities += 2L -> Business(0, "Acme", 0, 3, List(0))
    w.entities += 3L -> Building(0, Vec2f(6, 6), Direction.North, List(BuildingComponent(Vec2f(-2, 0), "wall"), BuildingComponent(Vec2f(0, 0), "wall"), BuildingComponent(Vec2f(-2, 2), "roof"), BuildingComponent(Vec2f(0, 2), "roof")), 0)
    World.assignBorders(w, None)
    w
  }

  private var tick = 0
  private var elapsedRealWorldTime = 0.0f

  override def create(): Unit = {
    LibGDXRender.initalize()
  }

  override def render(): Unit = {
    elapsedRealWorldTime += Gdx.graphics.getDeltaTime
    if(elapsedRealWorldTime >= 1) {
      tick += 1
      elapsedRealWorldTime = 0
    }

    SinglePlayerGame.update(world, tick)

    LibGDXRender.render(world, tick)
  }

  override def dispose(): Unit = {
    LibGDXRender.cleanup()
  }
}
