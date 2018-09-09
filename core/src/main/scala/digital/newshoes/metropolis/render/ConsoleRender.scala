package digital.newshoes.metropolis.render

import digital.newshoes.metropolis.state._

object ConsoleRender {
  def render(world:World, step:Int) {
    (1 to 5).foreach { _ => println("") }

    val drawBuffer = world.lots.map { l =>
//      if(l != null) {
//        l.glyph match {
//          case 0 => ' '
//          case 1 => '#'
//          case _ => '.'
//        }
//      } else ' '
    }

    // applies entity to drawbuffer
    for(entity <- world.entities.values) {
      entity match {
        case r:Resident =>
          val i = (world.size.y * r.position.x + r.position.y).toInt
          drawBuffer(i) = 'R'
        case b:Building =>
//          for(lot <- b.components) {
//            val i = (world.size.y * lot.x + lot.y).toInt
//            drawBuffer(i) = 'B'
//          }
        case _ =>
      }
    }

    println(s"== Step $step ==")
    for(line <- drawBuffer.grouped(world.size.x.toInt)) {
      println(line.mkString(" "))
    }
  }
}