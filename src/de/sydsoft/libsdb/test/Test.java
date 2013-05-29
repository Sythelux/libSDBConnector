package de.sydsoft.libsdb.test;

import de.sydsoft.libsdb.db.DBMySQL;
import de.sydsoft.libsdb.db.DBSQLiteJDBC;

public class Test {
	public static void main(String[] args) {
		boolean ret = false;
		// beispiel für SQLite
		DBSQLiteJDBC dbsqlite = new DBSQLiteJDBC("C:\\Path\\To\\SQLite\\Database\\testSQLite.db");
		ret = dbsqlite.createConnection();
		if (ret) {
			dbsqlite.sqlCommand("create table if not exists test ( nr int )");
			System.out.println(ret);
			dbsqlite.closeConnection();
		}

		// Beispiel für MySQL
		// variante 1
		DBMySQL dbmysql = new DBMySQL("localhost:3306/test");
		dbmysql.setUserName("mysql");
		dbmysql.setPasswort("mysql");
		// Variante 2
		dbmysql = new DBMySQL("localhost:3306/test", "root", "");
		
		ret = dbmysql.createConnection();
		if (ret) {
			System.out.println(ret);
			dbmysql.sqlCommand("create table if not exists test ( nr int )");
			dbmysql.closeConnection();
		}
	}
}
