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
    val lengthOfString = (x: String) => x.length == 3
    assert(dropWhile(a1, lengthOfString) == fpinscala.datastructures.List("cc", "cccc"))
    assert(dropWhile(a2, lengthOfString) == fpinscala.datastructures.List())
  }
}
