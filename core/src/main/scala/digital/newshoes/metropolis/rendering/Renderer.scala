package digital.newshoes.metropolis.rendering
import digital.newshoes.metropolis.state.World

trait Renderer {
  def initialize(): Unit

  def render(world: World, step: Int): Unit

  def cleanup(): Unit
}
