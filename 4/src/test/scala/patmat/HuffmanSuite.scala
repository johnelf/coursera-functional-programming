package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
  trait TestTrees {
    val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
    val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
  }

  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }

  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a','b','d'))
    }
  }

  test("times of a chars list") {
    assert(times(List('a', 'a', 'b', 'c')) === List(('a', 2), ('b', 1), ('c', 1)))
  }

  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
  }

  test("makeOrderedLeafList for some big frequency table") {
    assert(makeOrderedLeafList(List(('a', 4), ('y', 5), ('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3), Leaf('a', 4), Leaf('y', 5)))
  }

  test("combine of some leaf list") {
    val leafList = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leafList) === List(Fork(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4), List('e', 't', 'x'), 7)))
  }

  test("combine of some big leaf list") {
    val leafList = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4), Leaf('y', 5))
    assert(combine(leafList) === List(Fork(Leaf('y', 5), Fork(Fork(Leaf('e', 1), Leaf('t', 2), List('e', 't'), 3), Leaf('x', 4), List('e', 't', 'x'), 7), List('y', 'e', 't', 'x'), 12)))
  }

  test("create code tree from arbitary list") {
    val chars = List('a', 'a', 'a', 'a', 'b', 'b', 'b', 'b', 'b', 'c', 'd', 'd')
    assert(createCodeTree(chars) === Fork(Leaf('b', 5), Fork(Fork(Leaf('c', 1), Leaf('d', 2), List('c', 'd'), 3), Leaf('a', 4), List('c', 'd', 'a'), 7), List('b', 'c', 'd', 'a'), 12))
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }
}
