# libSDBConnector - library: Syds Database Connector
a java library to make Database connection in Java more easily (I know it sounds impossible, it is a bit)

## how to compile
it's a eclipse Projekt import it into eclipse and create a *.jar

## how to use
codeexample

```java

	public static void main(String[] args) {
		boolean ret = false;
		// example for SQLite
		DBSQLiteJDBC dbsqlite = new DBSQLiteJDBC("C:\\Path\\To\\SQLite\\Database\\testSQLite.db");
		ret = dbsqlite.createConnection();
		if (ret) {
			dbsqlite.sqlCommand("create table if not exists test ( nr int )");
			System.out.println(ret);
			dbsqlite.closeConnection();
		}

		// example for MySQL
		// variant 1
		DBMySQL dbmysql = new DBMySQL("localhost:3306/test");
		dbmysql.setUserName("mysql");
		dbmysql.setPasswort("mysql");
		// variant 2
		dbmysql = new DBMySQL("localhost:3306/test", "root", "");
		
		ret = dbmysql.createConnection();
		if (ret) {
			System.out.println(ret);
			dbmysql.sqlCommand("create table if not exists test ( nr int )");
			dbmysql.closeConnection();
		}
	}
```
