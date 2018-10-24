package digital.newshoes.metropolis.state

import upickle.default.{ReadWriter, macroRW}

case class Vec2f(var x: Float, var y: Float) {
  def product: Float = x * y
  def :=(other: Vec2f) {
    x = other.x
    y = other.y
  }
}

object Vec2f {
  implicit def rw: ReadWriter[Vec2f] = macroRW
}