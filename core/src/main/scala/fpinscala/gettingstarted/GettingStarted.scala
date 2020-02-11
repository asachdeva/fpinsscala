package fpinscala.gettingstarted

object MyModule extends App {
  def abs(n: Int): Int =
    if (n < 0) -n
    else n

  private def formatAbs(x: Int) = {
    val msg = "The absolute value of %d is %d"
    msg.format(x, abs(x))
  }

  private def formatFactorial(x: Int) = {
    val msg = "The factorial of %d is %d"
    msg.format(x, factorial(x))
  }

  def factorial(n: Int): Int = {
    def go(n: Int, acc: Int): Int =
      if (n <= 0) acc
      else go(n - 1, n * acc)

    go(n, 1)
  }

  // Ex 2.1
  def fib(n: Int): Int = {
    def loop(n: Int, prev: Int, curr: Int): Int =
      if (n == 0) prev
      else loop(n - 1, curr, prev + curr)
    loop(n, 0, 1)
  }

  def formatResult(name: String, n: Int, f: Int => Int) = {
    val msg = "The %s of %d is %d"
    msg.format(name, n, f(n))
  }

  println(formatAbs(-42))
  println(formatFactorial(7))
  println(formatResult("absolute", -42, abs))
  println(formatResult("factorial", 7, factorial))

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

  val findFirstResult = findFirst(Array(1, 3, 5, 7, 9), (x: Int) => x == 7)
  println(findFirstResult)

  val isSortedResult = isSorted(Array(1, 2, 3, 0, 9), (x: Int, y: Int) => x < y)
  println(isSortedResult)

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
