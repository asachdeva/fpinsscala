package com.fpinscala.datastructures

class TreeSuite extends munit.FunSuite {
  test("Tree Tests") {
    import fpinscala.datastructures.Tree._
    val t1 = branch(branch(branch(leaf(1), leaf(2)), leaf(3)), branch(leaf(5), leaf(6)))
    val t2 = branch(branch(branch(leaf(2), leaf(3)), leaf(4)), branch(leaf(6), leaf(7)))
    assert(size(t1) == 9)
    assert(sizeViaFold(t1) == 9)

    assert(maximum(t1) == 6)
    assert(maximumViaFold(t1) == 6)

    assert(depth(t1) == 3)
    assert(depthViaFold(t1) == 3)

    assert(map(t1)(_ + 1) == t2)
    assert(mapViaFold(t1)(_ + 1) == t2)
  }
}
