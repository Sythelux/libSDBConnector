package de.sydsoft.libsdb.datab;

/**
 * select().all().from("stuff").build();
 * select().column("id").as("").from("stuff").build();
 * 
 * @author sythelux
 *
 */
public class StatementBuilder {
	public static final String	SELECT	= "SELECT";
	public static final String	INSERT	= "INSERT";
	public static final String	WHERE	= "WHERE";
	public static final String	AS		= "AS";
	public static final String	FROM	= "FROM";
	public static final String	OR		= "OR";
	public static final String	IN		= "IN";
	public static final String	AND		= "AND";
	public static final String	ORDERBY	= "ORDER BY";
	public static final String	GROUPBY	= "GROUP BY";
	public static final String	LIKE	= "LIKE";
	public static final String	BETWEEN	= "BETWEEN";
	public static final String	DELETE	= "DELETE";

	private String	statement	= "";

	private StatementBuilder select() {
		statement += SELECT+" ";
		return this;
	}

	private <T> StatementBuilder all(T dest) {
		return this;
	}

	private StatementBuilder column(String colName) {
		return this;
	}

	private StatementBuilder as(String alias) {
		return this;
	}

	private StatementBuilder from(String tableName) {
		return this;
	}

	private StatementBuilder whereClause(WhereClauseBuilder builder) {
		return this;
	}

	private String build() {
		return statement;
	}
}
