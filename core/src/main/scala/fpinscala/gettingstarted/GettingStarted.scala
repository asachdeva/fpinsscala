package fpinscala.gettingstarted

object MyModule {

  //Monomorphic findfirst
  def findFirst(ss: Array[String], key: String): Int = {
    def loop(n: Int): Int =
      if (n >= ss.length) -1
      else if (ss(n) == key) n
      else loop(n + 1)

    loop(0)
  }

  //Polymorphic findfirst
  def findFirst[A](as: Array[A], p: A => Boolean): Int = {
    def loop(n: Int): Int =
      if (n >= as.length) -1
      else if (p(as(n))) n
      else loop(n + 1)

    loop(0)
  }

  // Ex 2.2
  def isSorted[A](as: Array[A], greaterThan: (A, A) => Boolean): Boolean = {
    def loop(n: Int): Boolean =
      if (n >= as.length - 1) true
      else if (greaterThan(as(n), as(n + 1))) false
      else loop(n + 1)

    loop(0)
  }

  // Ex 2.3
  def curry[A, B, C](f: (A, B) => C): A => (B => C) =
    a => b => f(a, b)

  // Ex 2.4
  def uncurry[A, B, C](f: A => B => C): (A, B) => C =
    (a, b) => f(a)(b)

  // Ex 2.5
  def compose[A, B, C](f: B => C, g: A => B): A => C =
    a => f(g(a))

}
