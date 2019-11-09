package digital.newshoes.metropolis.serialization

import java.io.{File, FileWriter}

import digital.newshoes.metropolis.state.World
import upickle.default._

object JsonWorldSerializer {

  def save(world: World, path: String): Unit = {
    val worldJson = write(world)
    val writer = new FileWriter(path)
    try {
      writer.write(worldJson)
    } finally {
      writer.close()
    }
  }

//  def load(path: String): World = {
//    val worldFile: File = new File(path)
//    read[World](worldFile)
//  }
}
