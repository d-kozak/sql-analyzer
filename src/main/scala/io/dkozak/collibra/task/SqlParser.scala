package io.dkozak.collibra.task

import io.dkozak.collibra.task.ast.{ColumnReference, ErrorNode, SelectStatement, TableRefence}

import scala.util.matching.Regex
import scala.util.parsing.combinator.RegexParsers

/**
 * Simple SQL select parser implemented using parser combinators
 * TODO add error recovery
 */
class SqlParser extends RegexParsers {

  /**
   * Entry point of the parsing, parses a single select query
   */
  def parseQuery(query: String): Either[ErrorNode, SelectStatement] = parse(selectStatement, query) match {
    case Success(ast, _) => Right(ast)
    case Failure(msg, _) =>
      System.err.println(s"FAILURE: $msg")
      Left(ErrorNode(msg))
    case Error(msg, _) =>
      System.err.println(s"ERROR: $msg")
      Left(ErrorNode(msg))
  }

  private def selectStatement: Parser[SelectStatement] = SELECT ~ columns ~ FROM ~ tables ^^ {
    case _ ~ cols ~ _ ~ tabs => SelectStatement(cols, tabs)
  }

  /**
   * case insensitive select keyword
   */
  private def SELECT = """(?i)SELECT""".r

  /**
   * case insensitive from keyword
   */
  private def FROM = """(?i)FROM""".r

  private def columns: Parser[List[ColumnReference]] = rep1sep(columnReference, ",")

  /**
   * Two forms supported
   * column vs tableSpecifier.column
   */
  private def columnReference: Parser[ColumnReference] = identifier ~ opt("." ~ identifier) ^^ {
    case tableName ~ Some("." ~ columnName) => ColumnReference(columnName, Some(tableName))
    case columnName ~ None => ColumnReference(columnName)
  }

  /**
   * a simple identifier, just a nonempty sequence of characters
   */
  private def identifier: Regex = "[a-z]+".r

  private def tables: Parser[List[TableRefence]] = rep1sep(tableReference, ",")

  private def tableReference: Parser[TableRefence] = identifier ^^ { ref => TableRefence(ref.toString) }


}

