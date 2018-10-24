package digital.newshoes.metropolis.serialization

import java.io.{File, FileWriter}

import digital.newshoes.metropolis.state.World
import upickle.default._

object JsonWorldSerializer extends WorldSerializer {

  def save(world: World, path: String): Unit = {
    val worldJson = write(world)
    val entityWriter = new FileWriter(path)
    try {
      entityWriter.write(worldJson)
    } finally {
      entityWriter.close()
    }
  }

  def load(path: String): World = {
    val worldFile: File = new File(path)
    read[World](worldFile)
  }
}
