package io.dkozak.collibra.task

object Main {
  def main(args: Array[String]) {
    val valid = List("SELECT a FROM t",
      "SELECT a,b FROM t,d",
      "SELECT a,b, c FROM t, d ,e"
    )
    val syntaxErrors = List("SELECTOS a FROM t")
    val sqlAnalyzer = new SqlAnalyzer
    sqlAnalyzer.runAnalysis(valid ++ syntaxErrors)
  }
}
