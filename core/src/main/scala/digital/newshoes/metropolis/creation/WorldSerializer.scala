package digital.newshoes.metropolis.creation

import digital.newshoes.metropolis.state.World

trait WorldSerializer {
  def save(world: World, path: String): Unit
  def load(path: String): World
}
