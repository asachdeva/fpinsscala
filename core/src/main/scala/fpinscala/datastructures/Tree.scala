package fpinscala.datastructures

// An ADT is just a data type defined by one or more data
// constructors each of which may conatin zero or more arguments
//
// We say that a data type is sum/union of its data constructors
// and each data constructor is the product of its arguments and
// hence the name Algebraic Data Type
//
sealed trait Tree[+A]
case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

object Tree {

  // Ex 3.25
  def size[A](t: Tree[A]): Int = t match {
    case Leaf(_)      => 1
    case Branch(l, r) => 1 + size(l) + size(r)
  }

  // Ex 3.26
  def maximum(t: Tree[Int]): Int = t match {
    case Leaf(value)  => value
    case Branch(l, r) => maximum(l).max(maximum(r))
  }

  // Ex 3.27
  def depth[A](t: Tree[A]): Int = t match {
    case Leaf(_)      => 0
    case Branch(l, r) => 1 + (depth(l).max(depth(r)))
  }

  // Ex 3.28
  def map[A, B](t: Tree[A])(f: A => B): Tree[B] = t match {
    case Leaf(a)      => Leaf(f(a))
    case Branch(l, r) => Branch(map(l)(f), map(r)(f))
  }

  // Helper Functions
  def leaf[A](a: A): Tree[A] = Leaf(a)
  def branch[A](l: Tree[A], r: Tree[A]): Tree[A] = Branch(l, r)

  // Ex 3.29
  def fold[A, B](t: Tree[A])(f: A => B)(g: (B, B) => B): B = t match {
    case Leaf(a)      => f(a)
    case Branch(l, r) => g(fold(l)(f)(g), fold(r)(f)(g))
  }

  def sizeViaFold[A](t: Tree[A]): Int =
    fold(t)(_ => 1)(1 + _ + _)

  def maximumViaFold(t: Tree[Int]): Int =
    fold(t)(a => a)(_.max(_))

  def depthViaFold[A](t: Tree[A]): Int =
    fold(t)(_ => 0)((d1, d2) => 1 + (d1.max(d2)))

  def mapViaFold[A, B](t: Tree[A])(f: A => B): Tree[B] =
    fold(t)(a => Leaf(f(a)): Tree[B])(Branch(_, _))
}
