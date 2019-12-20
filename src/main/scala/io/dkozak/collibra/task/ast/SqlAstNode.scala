package io.dkozak.collibra.task.ast


/**
 * TODO add error recovery -> make the ast support partially correct nodes using ErrorNode
 */
/**
 * Any AST node
 */
abstract class SqlAstNode

/**
 * Root of the ast
 */
case class SelectStatement(columns: List[ColumnReference], tables: List[TableRefence]) extends SqlAstNode


/**
 * Reference of a column, might contain a table identifier as well
 */
case class ColumnReference(columnName: String, tableName: Option[String] = None)

case class TableRefence(tableName: String)


/**
 * A subtree that was not successfully parsed. It could be used in the future to analyse even syntactically incorrect queries,
 * at least partially.
 */
case class ErrorNode(message: String) extends SqlAstNode