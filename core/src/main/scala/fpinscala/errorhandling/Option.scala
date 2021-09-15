package fpinscala.errorhandling

// This is meant to hide the default Option in scala package
import scala.{Either => _, Option => _, _}

sealed trait Option[+A] {
  // Ex 4.1
  def map[B](f: A => B): Option[B] =
    this match {
      case None    => None
      case Some(a) => Some(f(a))
    }

  // the default: => B means will not be evaluated until its
  // needed
  // The B >: A indicates that B must equal to or a supertype
  // of A
  def getOrElse[B >: A](default: => B): B =
    this match {
      case None    => default
      case Some(a) => a
    }

  def flatMap[B](f: A => Option[B]): Option[B] =
    map(f).getOrElse(None)
  def flatMap1[B](f: A => Option[B]): Option[B] =
    this match {
      case None    => None
      case Some(a) => f(a)
    }

  def orElse[B >: A](ob: => Option[B]): Option[B] =
    this.map(Some(_)).getOrElse(ob)
  def orElse1[B >: A](ob: => Option[B]): Option[B] =
    this match {
      case None => ob
      case _    => this
    }

  def filter(f: A => Boolean): Option[A] =
    this match {
      case Some(a) if f(a) => this
      case _               => None
    }
}

case object None extends Option[Nothing]
case class Some[+A](get: A) extends Option[A]

object Option {

  def mean(xs: Seq[Double]): Option[Double] =
    if (xs.isEmpty) None
    else Some(xs.sum / xs.length)

  // Ex 4.2
  def variance(xs: Seq[Double]): Option[Double] =
    mean(xs).flatMap(m => mean(xs.map(x => math.pow(x - m, 2))))

  // Ex 4.3
  def map2[A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] =
    a.flatMap(aa => b.map(bb => f(aa, bb)))

  def map3[A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] =
    for {
      aa <- a
      bb <- b
    } yield f(aa, bb)

  // Ex 4.4
  def sequence[A](a: List[Option[A]]): Option[List[A]] =
    a match {
      case Nil    => Some(Nil)
      case h :: t => h.flatMap(hh => sequence(t).map(hh :: _))
    }

  // Ex 4.5
  def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] =
    a.foldRight[Option[List[B]]](Some(Nil))((h, t) => map2(f(h), t)(_ :: _))

  def sequenceViaTraverse[A](a: List[Option[A]]): Option[List[A]] =
    traverse(a)(x => x)

}
