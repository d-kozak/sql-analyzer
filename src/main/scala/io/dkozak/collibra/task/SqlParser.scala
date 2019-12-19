package io.dkozak.collibra.task

import io.dkozak.collibra.task.ast.{ErrorNode, SelectStatement}

import scala.util.matching.Regex
import scala.util.parsing.combinator.RegexParsers

class SqlParser extends RegexParsers {
  def parseQuery(query: String): Either[ErrorNode, SelectStatement] = parse(select, query) match {
    case Success(ast, _) => Right(ast)
    case Failure(msg, _) =>
      System.err.println(s"FAILURE: $msg")
      Left(ErrorNode(msg))
    case Error(msg, _) =>
      System.err.println(s"ERROR: $msg")
      Left(ErrorNode(msg))
  }

  private def select: Parser[SelectStatement] = "SELECT" ~ columns ~ "FROM" ~ tables ^^ {
    case "SELECT" ~ cols ~ "FROM" ~ tabs => SelectStatement(cols, tabs)
  }

  private def columns: Parser[List[String]] = repsep(identifier, ",")

  private def identifier: Regex = "[a-z]+".r

  private def tables: Parser[List[String]] = repsep(identifier, ",")
}
