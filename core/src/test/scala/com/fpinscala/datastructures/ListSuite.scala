package com.fpinscala.datastructures

import fpinscala.datastructures.List._

class ListSuite extends munit.FunSuite {
  test("Ex 3.2 tail test") {
    assert(tail(fpinscala.datastructures.List(1, 2, 3)) == fpinscala.datastructures.List(2, 3))

    val caught = intercept[RuntimeException] {
      tail(fpinscala.datastructures.List())
    }
    assert(caught.getMessage() == "Tail on an empty list")
  }

  test("Ex 3.3 setHead test") {
    assert(setHead("a", fpinscala.datastructures.List("b", "c")) == fpinscala.datastructures.List("a", "c"))

    val caught = intercept[RuntimeException] {
      setHead("a", fpinscala.datastructures.List())
    }
    assert(caught.getMessage == "Trying to set head on an empty list")
  }

  test("Ex 3.4 drop test") {
    assert(drop(fpinscala.datastructures.List("b", "c"), 1) == fpinscala.datastructures.List("c"))
    assert(drop(fpinscala.datastructures.List("b", "c"), 0) == fpinscala.datastructures.List("b", "c"))
    assert(drop(fpinscala.datastructures.List(), 10) == fpinscala.datastructures.List())
  }

  test("Ex 3.4 dropWhile test") {
    val a1 = fpinscala.datastructures.List("aaa", "bbb", "cc", "cccc")
    val a2 = fpinscala.datastructures.List("aaa", "bbb", "ccc")
    val a3 = fpinscala.datastructures.List(1, 2, 3, 4, 5)
    val ex1 = dropWhile(a3, (x: Int) => x < 4)
    val lengthOfString = (x: String) => x.length == 3
    assert(dropWhile(a1, lengthOfString) == fpinscala.datastructures.List("cc", "cccc"))
    assert(dropWhile(a2, lengthOfString) == fpinscala.datastructures.List())
    assert(ex1 == fpinscala.datastructures.List(4, 5))
  }

  test("Ex 3.6 Init test") {
    val a1 = fpinscala.datastructures.List(1, 2, 3, 4)
    val a2 = fpinscala.datastructures.List(1)
    assert(init(a1) == fpinscala.datastructures.List(1, 2, 3))
    assert(init(a2) == fpinscala.datastructures.List())
  }

  test("Ex 3.9 Length test") {
    val a1 = fpinscala.datastructures.List(1, 2, 3)
    assert(length(a1) == 3)
  }

  test("Ex 3.11 FoldLeft tests") {
    val a1 = fpinscala.datastructures.List(1, 2, 3, 4)
    val a2 = fpinscala.datastructures.List(1.0, 2.0, 3.0, 4.0)
    assert(length2(a1) == 4)
    assert(sum3(a1) == 10)
    assert(product3(a2) == 24.0)
  }

  test("Ex 3.12 Reverse test") {
    val a1 = fpinscala.datastructures.List(1, 2, 3, 4)
    assert(reverse(a1) == fpinscala.datastructures.List(4, 3, 2, 1))
  }

  test("Ex 3.14 Append test") {
    val a1 = fpinscala.datastructures.List(1, 2)
    val a2 = fpinscala.datastructures.List(3, 4)
    assert(appendViaFoldright(a1, a2) == fpinscala.datastructures.List(1, 2, 3, 4))
  }

  test("Ex 3.15 Concat test") {
    val a1 = fpinscala.datastructures.List(fpinscala.datastructures.List(1, 2), fpinscala.datastructures.List(3, 4))
    assert(concat(a1) == fpinscala.datastructures.List(1, 2, 3, 4))
  }

  test("Ex 3.16 add1 test") {
    val a1 = fpinscala.datastructures.List(1, 2)
    assert(add1(a1) == fpinscala.datastructures.List(2, 3))
  }

  test("Ex 3.17 test") {
    val a1 = fpinscala.datastructures.List(1.0, 2.0, 3.0)
    assert(doubleToString(a1) == fpinscala.datastructures.List("1.0", "2.0", "3.0"))
  }
}
