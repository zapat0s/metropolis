package digital.newshoes.metropolis.rendering

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{Sprite, SpriteBatch}
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera, Texture}
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.maps.tiled.{TiledMap, TmxMapLoader}
import digital.newshoes.metropolis.state._

import scala.collection.mutable

object LibGDXRenderer extends Renderer {
  private var batch: SpriteBatch = _
  private var camera: OrthographicCamera = _

  private var manTexture: Texture = _
  private var manSprite: Sprite = _

  private var buildingTexture: Texture = _

  private var buildingSprites: mutable.Map[String, Sprite] = _

  private var map: TiledMap = _
  private var mapRenderer: OrthogonalTiledMapRenderer = _

  def initialize(): Unit = {
    batch = new SpriteBatch

    manTexture = new Texture("soldier.png")
    manSprite = new Sprite(manTexture, 16, 200, 50, 56)
    manSprite.setSize(1.0f, 1.3f)

    buildingSprites = new mutable.HashMap()
    buildingTexture = new Texture("house.png")
    val wallSprite = new Sprite(buildingTexture, 96, 96)
    wallSprite.setSize(2.0f, 2.0f)
    buildingSprites.put("wall", wallSprite)

    val roofSprite = new Sprite(buildingTexture, 0, 96, 96, 96)
    roofSprite.setSize(2.0f, 2.0f)
    buildingSprites.put("roof", roofSprite)

    map = new TmxMapLoader().load("test.tmx")
    mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 32f)

    camera = new OrthographicCamera()
    camera.setToOrtho(false, 30, 20)
    camera.translate(-2.5f, 0)
  }

  def render(world: World, step: Int): Unit = {
    camera.update()
    batch.setProjectionMatrix(camera.combined)

    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    mapRenderer.setView(camera)
    mapRenderer.render()

    batch.begin()
    Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0)

    world.entities.foreach(e => e._2 match {
      case r: Resident => renderResident(r, batch)
      case b: Building => renderBuilding(b, batch)
      case _: Business => ()
      case _ => Gdx.app.log("LibGDXRender", "Unknown Entity type encountered.")
    })

    batch.end()
  }

  private def renderResident(resident: Resident, spriteBatch: SpriteBatch): Unit = {
    manSprite.setPosition(resident.position.x, resident.position.y)
    manSprite.draw(spriteBatch)
  }

  def renderBuilding(b: Building, batch: SpriteBatch): Unit = {
    b.components.foreach(c => {
      val sprite: Sprite = buildingSprites(c.sprite)
      val position: Vec2f = Vec2f(b.position.x + c.position.x, b.position.y + c.position.y)
      sprite.setPosition(position.x, position.y)
      sprite.draw(batch)
    })
  }

  def cleanup(): Unit = {
    batch.dispose()
    manTexture.dispose()
  }

  def updateCameraOrientation(position: (Int, Int), rotation: Int, zoom: Int): Unit = {
    camera.position.x = position._1
    camera.position.y = position._2
  }
}
