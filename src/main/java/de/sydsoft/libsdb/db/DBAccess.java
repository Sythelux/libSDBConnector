package de.sydsoft.libsdb.db;

import java.util.Properties;

/**
 * 
 * @author Sythelux Rikd
 */
public class DBAccess extends DB {
	/** Standardkonstruktor */
	public DBAccess() {
		super();
		init();
	}

	/**
	 * Konstruktor der eine Zeichenkette mit dem Pfad und Dateinamen der
	 * Datenbank annimmt
	 * 
	 * @param dbName
	 *            Pfad zur Datei mit Dateinamen
	 */
	public DBAccess(String dbName) {
		super(dbName);
		connProps = new Properties();
		connProps.put("charSet", "Latin1");
		init();
	}

	public DBAccess(String dbName, Properties props) {
		super(dbName, props);
		init();
	}

	private void init() {
		connUrl = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
		connUrlClose = ";DriverID=22}";
		driver = "sun.jdbc.odbc.JdbcOdbcDriver";
	}
}
