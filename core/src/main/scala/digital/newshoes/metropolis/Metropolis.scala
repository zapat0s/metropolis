package digital.newshoes.metropolis

import com.badlogic.gdx.{ApplicationAdapter, Gdx}
import com.badlogic.gdx.graphics.{GL20, Texture}
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import digital.newshoes.metropolis.render.ConsoleRender
import digital.newshoes.metropolis.state._

class Metropolis extends ApplicationAdapter {
  private var batch:SpriteBatch = _
  private var img:Texture = _

  private val world = {
    val w = new World(size = Vec2i(16, 16))
    w.entities += 0L -> Resident(0,"Bobbie", Vec2i(1, 1), Some(Vec2i(1, 1)), 1, 2)
    w.entities += 1L -> Building(0, List(Vec2i(2, 2), Vec2i(2, 3)), 0)
    w.entities += 2L -> Business(0, "Acme", 0, 3, List(0))
    w.entities += 3L -> Building(0, List(Vec2i(5, 2), Vec2i(5, 3), Vec2i(6, 2), Vec2i(6, 3)), 0)
    World.assignBorders(w, Lot(None, 1))
    w
  }

  private var tick = 0

  override def create(): Unit = {
    batch = new SpriteBatch
    img = new Texture("badlogic.jpg")
  }

  override def render(): Unit = {
    SinglePlayerGame.update(world, tick)
    tick += 1

    ConsoleRender.render(world, tick)

    Gdx.gl.glClearColor(1, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    batch.begin()
    batch.draw(img, 0, 0)
    batch.end()

  }

  override def dispose(): Unit = {
    batch.dispose()
    img.dispose()
  }
}
