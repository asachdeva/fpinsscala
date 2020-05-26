package com.fpinscala.gettingstarted

import fpinscala.gettingstarted.MyModule._

class Chapter2Suite extends munit.FunSuite {
  test("Ex 2.1") {

    assert(findFirst(Array("a", "b", "c"), "d") == -1)
    assert(findFirst(Array("a", "b", "c"), "c") == 2)
    assert(findFirst(Array(1, 2, 3), (x: Int) => x == 2) == 1)
  }

  test("Ex 2.2") {
    assert(isSorted(Array(1, 2, 3), (x: Int, y: Int) => x > y) == true)
    assert(isSorted(Array(1, 3, 2), (x: Int, y: Int) => x > y) == false)
  }
}
