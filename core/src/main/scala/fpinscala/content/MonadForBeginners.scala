package fpinscala
package content

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
//import scala.util.Success

object MonadForBeginners {
  case class SafeValue[+T](private val internalValue: T) { // constructor or pure or unit
    def get: T = synchronized {
      internalValue
    }

    def flatMap[S](transformer: T => SafeValue[S]): SafeValue[S] = synchronized { // bind or flatMap
      transformer(internalValue)
    }
  }

  // external API
  def gimmeSafeValue[T](value: T): SafeValue[T] = SafeValue(value)
  val safeString: SafeValue[String] = gimmeSafeValue("Scala is awesome")

  // extract
  val string = safeString.get
  // transform
  val upperString = string.toUpperCase()
  // wrap
  val safeUpperString = SafeValue(upperString)

  // ETW
  val safeUpperString2 = safeString.flatMap(s => SafeValue(s.toUpperCase()))

  // Examples
  case class Person(firstName: String, lastName: String) {
    assert(firstName != null && lastName != null)
  }

  def getPerson(firstName: String, lastName: String): Person =
    if (firstName != null) {
      if (lastName != null) {
        Person(firstName, lastName)
      } else {
        null
      }
    } else {
      null
    }

  def getBetterPerson(firstName: String, lastName: String): Option[Person] =
    Option(firstName).flatMap(fName => Option(lastName).flatMap(lName => Option(Person(fName, lName))))

  def getBetterPerson2(firstName: String, lastName: String): Option[Person] =
    for {
      fName <- Option(firstName)
      lName <- Option(lastName)
    } yield (Person(fName, lName))

  // Example 1
  case class User(id: String)
  case class Product(sku: String, price: Double)

  def getUser(url: String): Future[User] = Future {
    println(url)
    User("akshay")
  }

  def getLastOrder(userId: String): Future[Product] = Future {
    println(userId)
    Product("123-456", 99.99)
  }

  val danielsUrl = "my.store.com/daniel"

  // getUser(danielsUrl).onComplete {
  // case Success(User(id)) =>
  // val lastOrder = getLastOrder(id)
  // lastOrder.onComplete {
  // case Success(Product(_, price)) =>
  // val vatIncludedPrice = price * 1.19
  // }}
  //

  // Example 2
  val vatIncludedPrice: Future[Double] = getUser(danielsUrl).flatMap(user => getLastOrder(user.id)).map(_.price * 1.19)

  val vatIncludedPriceFor: Future[Double] = for {
    user <- getUser(danielsUrl)
    product <- getLastOrder(user.id)
  } yield (product.price * 1.19)

  // Example 3 doubel for loops

  val numbers = List(1, 2, 3)
  val chars = List('a', 'b', 'c')

  val checkerBoard = numbers.flatMap(number => chars.map(char => (number, char)))

  val checkBoard2 = for {
    number <- numbers
    char <- chars
  } yield (number, char)

  // Laws of Monads
  // - left identity

  def twoConsecutive(x: Int) = List(x, x + 1)
  twoConsecutive(2)

  // Monad(x).flatMap(f) == f(x)

  // - right Identity
  List(1, 2, 3).flatMap(x => List(x))
  // Monad(x).flatMap(x => Monad(x) == Monad(x)

  // Associativity
  val incrementer = (x: Int) => List(x, x + 1)
  val doubler = (x: Int) => List(x, x * 2)

  def main(args: Array[String]): Unit =
    println(numbers.flatMap(incrementer).flatMap(doubler))

  /*
   * It is a design pattern for decoupling the context of a computation from the computation
   *
   * just a monoid in the category of endofunctors
   */

  // Put things into them
  // can flatMap them
  // can map them
  List(1, 2, 3)
  Future.successful(1)
  Option(1)

  // what is a monad?
  // Decoupling Combination Wrapper
  //
  // A monad is a wy to sequence computations
  //
  // Try to think of a Monad that does not involve failure
  //
  case class Id[A](a: A) {
    def flatMap[B](f: A => Id[B]): Id[B] =
      f(a)
  }

  object Id {
    def pure[A](a: A): Id[A] = Id(a)

  }

  case class Try[A](a: A) {
    def flatMap[B](f: A => Try[B]): Try[B] = ???
    def pure(a: A): Try[A] = ???
  }

  sealed trait Maybe[+A] {
    def flatMap[B](f: A => Maybe[B]): Maybe[B] = this match {
      case IsNull  => IsNull
      case Just(a) => f(a)
    }
  }

  case object IsNull extends Maybe[Nothing]
  case class Just[A](a: A) extends Maybe[A]
  // Monads
  // Try
  // Option --> possiblity of null
  // Either
  // Eval --> when to evaluate
  // Functtion1 = Reader --> essence of dependency
  // Decoder --> potential A that might come from a Json
  // Free --> decoupling Monad itself
  // (A, B)
  // List
  // EmptyList

}
