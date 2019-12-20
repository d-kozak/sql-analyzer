package io.dkozak.collibra.task

/**
 * Entry point of the program, runs analysis over selected queries
 */
object Main {
  def main(args: Array[String]) {
    val valid = List("SELECT a FROM t",
      "SELECT a,b FROM t,d",
      "SELECT a,b, c FROM t, d ,e",
      "select a,c,d from e,f",
      "sEleCt x,g fROm g,h"
    )
    val syntaxErrors = List("SELECTOS a FROM t")
    val sqlAnalyzer = new SqlAnalyzer
    sqlAnalyzer.runAnalysis(valid ++ syntaxErrors)
  }
}
