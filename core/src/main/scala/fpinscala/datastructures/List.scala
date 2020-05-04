package fpinscala.datastructures

// adding sealed in front of trait means
// all implementations of the trait must be defined
// in the file

// The + in front of A indicates that A is a covariant or
// positive parameter of List  So List[Dog] is considered
// a subtype of List[Animal] assuming Dog is a subtype of
// Animal
// Leaving off the + means invariant
// and adding a - means contravariant
sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List {
  def sum(ints: List[Int]): Int =
    ints match {
      case Nil        => 0
      case Cons(h, t) => h + sum(t)
    }

  def product(ds: List[Double]): Double =
    ds match {
      case Nil          => 1.0
      case Cons(0.0, _) => 0.0
      case Cons(h, t)   => h * product(t)
    }

  def apply[A](as: A*): List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))

  // Ex 3.1
  val x = List(1, 2, 3, 4, 5) match {
    case Cons(x, Cons(2, Cons(4, _)))          => x
    case Nil                                   => 42
    case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
    case Cons(h, t)                            => h + sum(t)
    case _                                     => 101
  }

  // Ex 3.2
  def tail[A](as: List[A]): List[A] =
    as match {
      case Nil        => sys.error("Tail on an empty list")
      case Cons(_, t) => t
    }

  // Ex 3.3
  def setHead[A](value: A, as: List[A]): List[A] =
    as match {
      case Nil        => sys.error("Trying to set head on an empty list")
      case Cons(_, t) => Cons(value, t)
    }

  // Ex 3.4
  def drop[A](l: List[A], n: Int): List[A] =
    if (n <= 0) l
    else
      l match {
        case Nil        => Nil
        case Cons(_, t) => drop(t, n - 1)
      }

  // Ex 3.5
  def dropWhile[A](l: List[A], f: A => Boolean): List[A] =
    l match {
      case Cons(h, t) if f(h) => dropWhile(t, f)
      case _                  => l
    }

  def append[A](a1: List[A], a2: List[A]): List[A] =
    a1 match {
      case Nil        => a2
      case Cons(h, t) => Cons(h, append(t, a2))
    }

  /// Ex 3.6
  // This could result in a stackOverflow exception
  def init[A](l: List[A]): List[A] =
    l match {
      case Nil          => sys.error("init on an empty list")
      case Cons(_, Nil) => Nil
      case Cons(h, t)   => Cons(h, init(t))
    }

  def init2[A](l: List[A]): List[A] = {
    import collection.mutable.ListBuffer
    val buf = new ListBuffer[A]
    def go(cur: List[A]): List[A] =
      cur match {
        case Nil          => sys.error("init on an empty list")
        case Cons(_, Nil) => List(buf.toList: _*)
        case Cons(h, t)   => buf += h; go(t)
      }
    go(l)
  }

  def foldRight[A, B](as: List[A], z: B)(f: (A, B) => B): B =
    as match {
      case Nil        => z
      case Cons(h, t) => f(h, foldRight(t, z)(f))
    }

  def sum2(as: List[Int]): Int =
    foldRight(as, 0)((x, y) => x + y)

  def product2(ds: List[Double]): Double =
    foldRight(ds, 1.0)(_ * _)

  // Ex 3.9
  def length[A](as: List[A]): Int =
    foldRight(as, 0)((_, acc) => acc + 1)

  // Ex 3.10
  def foldLeft[A, B](as: List[A], z: B)(f: (B, A) => B): B =
    as match {
      case Nil        => z
      case Cons(h, t) => foldLeft(t, f(z, h))(f)
    }

  // Ex 3.11
  def sum3(as: List[Int]): Int =
    foldLeft(as, 0)(_ + _)
  def product3(as: List[Double]): Double =
    foldLeft(as, 1.0)(_ * _)
  def length2[A](as: List[A]): Int =
    foldLeft(as, 0)((acc, _) => acc + 1)

  // Ex 3.12
  def reverse[A](as: List[A]): List[A] =
    foldLeft(as, List[A]())((acc, h) => Cons(h, acc))

  // Ex 3.13
  def foldRightViaFoldLeft[A, B](l: List[A], z: B)(f: (A, B) => B): B =
    foldLeft(reverse(l), z)((b, a) => f(a, b))

  // Ex 3.14
  def appendViaFoldright[A](a1: List[A], a2: List[A]): List[A] =
    foldRight(a1, a2)(Cons(_, _))

  // Ex 3.15
  def concat[A](as: List[List[A]]): List[A] =
    foldRight(as, Nil: List[A])(append)

  // Ex 3.16
  def add1(l: List[Int]): List[Int] =
    foldRight(l, Nil: List[Int])((h, t) => Cons(h + 1, t))

  // Ex 3.17
  def doubleToString(d: List[Double]): List[String] =
    foldRight(d, Nil: List[String])((h, t) => Cons(h.toString, t))

  // Ex 3.18
  def map[A, B](l: List[A])(f: A => B): List[B] =
    foldRight(l, Nil: List[B])((h, t) => Cons(f(h), t))
  def map1[A, B](l: List[A])(f: A => B): List[B] =
    foldRightViaFoldLeft(l, Nil: List[B])((h, t) => Cons(f(h), t))
  def map2[A, B](l: List[A])(f: A => B): List[B] = {
    val buf = new collection.mutable.ListBuffer[B]
    def go(l: List[A]): Unit =
      l match {
        case Nil        => ()
        case Cons(h, t) => buf += f(h); go(t)
      }
    go(l)
    List(buf.toList: _*)
  }

  // Ex 3.19
  def filter[A](as: List[A])(f: A => Boolean): List[A] =
    foldRight(as, Nil: List[A])((h, t) => if (f(h)) Cons(h, t) else t)
  def filter2[A](as: List[A])(f: A => Boolean): List[A] =
    foldRightViaFoldLeft(as, Nil: List[A])((h, t) => if (f(h)) Cons(h, t) else t)
  def filter3[A](as: List[A])(f: A => Boolean): List[A] = {
    def buf = new collection.mutable.ListBuffer[A]
    def go(l: List[A]): Unit =
      l match {
        case Nil        => ()
        case Cons(h, t) => if (f(h)) buf += h; go(t)
      }
    go(as)
    List(buf.toList: _*)
  }

  // Ex 3.20
  def flatMap[A, B](as: List[A])(f: A => List[B]): List[B] =
    foldRight(as, Nil: List[B])((h, t) => append(f(h), t))
  def flatMap2[A, B](as: List[A])(f: A => List[B]): List[B] =
    concat(map(as)(f))

  // Ex 3.21
  def filterViaFlatMap[A](l: List[A])(f: A => Boolean): List[A] =
    flatMap(l)(a => if (f(a)) List(a) else Nil)

  // Ex 3.22
  def addPairWise(a: List[Int], b: List[Int]): List[Int] =
    (a, b) match {
      case (Nil, _)                     => Nil
      case (_, Nil)                     => Nil
      case (Cons(h1, t1), Cons(h2, t2)) => Cons(h1 + h2, addPairWise(t1, t2))
    }

  // Ex 3.23
  def zipWith[A, B, C](a: List[A], b: List[B])(f: (A, B) => C): List[C] =
    (a, b) match {
      case (Nil, _)                     => Nil
      case (_, Nil)                     => Nil
      case (Cons(h1, t1), Cons(h2, t2)) => Cons(f(h1, h2), zipWith(t1, t2)(f))
    }

  // Ex 3.24
  def startsWith[A](l: List[A], prefix: List[A]): Boolean =
    (l, prefix) match {
      case (_, Nil)                              => true
      case (Cons(h, t), Cons(h2, t2)) if h == h2 => startsWith(t, t2)
      case _                                     => false
    }
  def hasSubSequence[A](sup: List[A], sub: List[A]): Boolean =
    sup match {
      case Nil                       => sub == Nil
      case _ if startsWith(sup, sub) => true
      case Cons(_, t)                => hasSubSequence(t, sub)
    }
}
