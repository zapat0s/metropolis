package digital.newshoes.metropolis

import com.badlogic.gdx.{ApplicationAdapter, Gdx}
import digital.newshoes.metropolis.creation.JsonWorldSerializer
import digital.newshoes.metropolis.render.LibGDXRender

class Metropolis extends ApplicationAdapter {
  private val world = JsonWorldSerializer.load("testWorld.json")

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
