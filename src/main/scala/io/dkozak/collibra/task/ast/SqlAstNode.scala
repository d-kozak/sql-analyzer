package io.dkozak.collibra.task.ast


/**
 * Represents any AST node
 */
abstract class SqlAstNode

/**
 * Root of the ast
 */
case class SelectStatement(columns: List[String], tables: List[String]) extends SqlAstNode

/**
 * Represents a subtree that was not succesfully parsed. It could be used in the future to analyse even syntactically incorrect queries,
 * at least partially.
 */
case class ErrorNode(message: String) extends SqlAstNode