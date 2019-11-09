package digital.newshoes.metropolis.serialization

import java.io.FileReader

import digital.newshoes.metropolis.state.World
import org.scalatest. FunSuite

class JsonWorldSerializerTest extends FunSuite {

  test("A JsonWorldSerializer should write out the world state to json file.") {
    val filename = "test.json"
    val world = World(null, null, null, null)
    JsonWorldSerializer.save(world, filename)
  }

}
