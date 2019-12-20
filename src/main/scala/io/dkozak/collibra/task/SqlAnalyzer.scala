package io.dkozak.collibra.task

import io.dkozak.collibra.task.ast.{ColumnReference, ErrorNode, SelectStatement, TableRefence}


/**
 * Result of the analysis of one query, contains columns and tables that were encountered
 */
case class QueryMapping(tables: Set[TableRefence], columns: Set[ColumnReference])

/**
 * Class running a simple analysis algorithm which counts the number of unique columns and tables
 * found in a set of queries. If only one query is given, the algorithm only prints the number of encountered columns.
 *
 */
class SqlAnalyzer {
  private val parser = new SqlParser

  def runAnalysis(query: String): QueryMapping = runAnalysis(query, parser.parseQuery(query))

  def runAnalysis(queryList: List[String]): List[QueryMapping] = {
    val rootNodes = queryList.map(parser.parseQuery)
    val mapping = queryList.zip(rootNodes)
      .map(x => runAnalysis(x._1, x._2))

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
      case Left(_) =>
        println("Query is invalid, nothing to analyze")
        QueryMapping(Set(), Set())

    }
  }
}
