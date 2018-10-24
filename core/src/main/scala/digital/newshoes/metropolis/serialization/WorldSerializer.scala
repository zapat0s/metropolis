package digital.newshoes.metropolis.serialization

import digital.newshoes.metropolis.state.World

trait WorldSerializer {
  def save(world: World, path: String): Unit

  def load(path: String): World
}
