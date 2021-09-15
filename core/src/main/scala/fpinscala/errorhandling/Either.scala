package fpinscala.errorhandling

import scala.{Either => _, Option => _, _}

sealed trait Either[+E, +A] {
  // Ex 4.6
  def map[B](f: A => B): Either[E, B] =
    this match {
      case Right(a) => Right(f(a))
      case Left(e)  => Left(e)
    }

  def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B] =
    this match {
      case Left(e)  => Left(e)
      case Right(a) => f(a)
    }

  def orElse[EE >: E, AA >: A](b: => Either[EE, AA]): Either[EE, AA] =
    this match {
      case Left(_)  => b
      case Right(a) => Right(a)
    }

  def map2[EE >: E, B, C](b: Either[EE, B])(f: (A, B) => C): Either[EE, C] =
    for {
      a <- this
      b1 <- b
    } yield f(a, b1)
}

object Either {

  def traverse[E, A, B](es: List[A])(f: A => Either[E, B]): Either[E, List[B]] =
    es.foldRight[Either[E, List[B]]](Right(Nil))((a, b) => f(a).map2(b)(_ :: _))

  def traverse_1[E, A, B](es: List[A])(f: A => Either[E, B]): Either[E, List[B]] =
    es match {
      case Nil    => Right(Nil)
      case h :: t => f(h).map2(traverse_1(t)(f))(_ :: _)
    }

  def sequence[E, A](es: List[Either[E, A]]): Either[E, List[A]] =
    traverse(es)(x => x)
}

case class Left[+E](get: E) extends Either[E, Nothing]
case class Right[+A](get: A) extends Either[Nothing, A]
