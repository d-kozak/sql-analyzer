package io.dkozak.collibra.task.ast

abstract class SqlAstNode

case class SelectStatement(columns: List[String], tables: List[String]) extends SqlAstNode

case class ErrorNode(message: String) extends SqlAstNode