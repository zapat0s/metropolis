package digital.newshoes.metropolis.state

import upickle.default.{ReadWriter => RW, macroRW}

case class Vec2f(var x: Float, var y: Float) {
  def product: Float = x * y
  def :=(other: Vec2f) : Unit = {
    x = other.x
    y = other.y
  }
}

object Vec2f {
  implicit val rw: RW[Vec2f] = macroRW
}