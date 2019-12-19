package io.dkozak.collibra.task

import io.dkozak.collibra.task.ast.{ErrorNode, SelectStatement}

case class QueryMapping(tables: Set[String], columns: Set[String])

class SqlAnalyzer {
  private val parser = new SqlParser

  def runAnalysis(query: String): QueryMapping = runAnalysis(query, parser.parseQuery(query))

  def runAnalysis(queryList: List[String]): List[QueryMapping] = {
    val rootNodes = queryList.map(parser.parseQuery)
    val mapping = queryList.zip(rootNodes)
      .map({ x => runAnalysis(x._1, x._2) })

    val uniqueTables = mapping.flatMap({
      _.tables
    }).toSet
    val uniqueColumns = mapping.flatMap({
      _.columns
    }).toSet

    println(
      s"""Analysis over multiple queries:
         |Tables:${uniqueTables}
         |Columns:${uniqueColumns}
         |Relationship base:${mapping}
         |""".stripMargin)

    mapping
  }

  private def runAnalysis(query: String, parseResult: Either[ErrorNode, SelectStatement]): QueryMapping = {
    println(s"Processing query '$query'")
    println(s"Ast: $parseResult")
    parseResult match {
      case Right(SelectStatement(columns, tables)) =>
        val mapping = QueryMapping(tables.toSet, columns.toSet)
        println(s"This query has ${mapping.columns.size} unique columns in it: ${mapping.columns}")
        mapping
      case Left(value) =>
        println("Query is invalid, nothing to analyze")
        QueryMapping(Set(), Set())

    }
  }
}
