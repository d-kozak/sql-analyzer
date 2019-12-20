package io.dkozak.collibra.task

import org.scalatest.FunSuite

/**
 * Tests of the parser
 */
class SqlParserTest extends FunSuite {

  val parser = new SqlParser

  test("SELECT a FROM t") {
    assert(parser.parseQuery("SELECT a FROM t").isRight)
  }

  test("SELECT a,b FROM t,d") {
    assert(parser.parseQuery("SELECT a,b FROM t,d").isRight)
  }

  test("SELECT a,b, c FROM t, d ,e") {
    assert(parser.parseQuery("SELECT a,b, c FROM t, d ,e").isRight)
  }

  test("select a,c,d from e,f") {
    assert(parser.parseQuery("select a,c,d from e,f").isRight)
  }

  test("sEleCt x,g fROm g,h") {
    assert(parser.parseQuery("sEleCt x,g fROm g,h").isRight)
  }

  test("SELECTOS a FROM t") {
    assert(parser.parseQuery("SELECTOS a FROM t").isLeft)
  }

  test("SELECT a FRM t") {
    assert(parser.parseQuery("SELECT a FRM t").isLeft)
  }

  test("SELECT a FROM ") {
    assert(parser.parseQuery("SELECT a FROM ").isLeft)
  }

  test("SELECT  FROM a") {
    assert(parser.parseQuery("SELECT  FROM a").isLeft)
  }
}
