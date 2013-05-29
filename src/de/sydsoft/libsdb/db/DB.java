/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.libsdb.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.sqlite.SQLite;

/**
 * 
 * @author Rikd
 */
public abstract class DB implements KeyWords {

	/** DatenbankName */
	protected String dbName;
	/** anfang der VerbindungsURL z.B.:"jdbc:mysql:"(bei MySQL) */
	protected String connUrl;
	/** ende der VerbindungsURL z.B.:";DriverID=22}"(bei Access) */
	protected String connUrlClose;
	/** Verbindung zur Datenbank */
	protected Connection connect;
	/** Beinhaltet die SQL abfrage */
	protected Statement command;
	/** Beinhaltet die Rückgabe der Daten */
	protected ResultSet data;
	/** beinhaltet die Metadaten der zurückgegebenen Daten */
	protected ResultSetMetaData resm;
	/**
	 * Properties datei die Bei datenbanken mitgegeben wird die kein namen und
	 * passwort benötigen in ihr kann z.B.: der "charSet" stehen mit "UTF-8"
	 */
	protected Properties connProps = new Properties();
	/** zugriffstreiber zum Initialisieren standartmäßig bei jeder Tochterklasse */
	protected String driver;

	/**
	 * 
	 * Konstruktor der eine Zeichenkette mit dem Pfad und Dateinamen der
	 * Datenbank annimmt
	 * 
	 * @param dbName
	 *            Pfad zur Datei mit Dateinamen
	 */
	public DB(String dbName) {
		this.dbName = dbName;
		connProps.put("charSet", "UTF-8");
	}

	/**
	 * 
	 * Konstruktor der eine Zeichenkette mit dem Pfad und Dateinamen der
	 * Datenbank annimmt
	 * 
	 * @param dbName
	 *            Pfad zur Datei mit Dateinamen
	 * @param props
	 */
	public DB(String dbName, Properties props) {
		this.dbName = dbName;
		connProps = props;
		connProps.put("charSet", "UTF-8");
	}

	/** Standardkonstruktor */
	public DB() {
	}

	/**
	 * baut eine Verbindung zu Datenbank auf
	 * 
	 * @return Wahrheitswert ob das verbinden erfolgreich war
	 */
	public boolean createConnection() {
		long startTime = System.currentTimeMillis();
		boolean isconnected = false;
		try {
			if (connect == null || !connect.isValid(0)) {
				Class.forName(driver).newInstance();
				connect = DriverManager.getConnection(connUrl + dbName + connUrlClose, connProps);
				createCommand();
			}
			isconnected = true;
			System.out.println("Connection opened. Duration: " + (System.currentTimeMillis() - startTime) + " ms");
		} catch (Exception e) {
//			System.err.println("couldn't open Database: " + connUrl + dbName + connUrlClose + " Error: " + e.getMessage());
			e.printStackTrace();
		}
		return isconnected;
	}

	protected boolean createCommand() {
		try {
			command = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	/**
	 * schließt die Verbindung zur Datenbank, wenn eine Verbindung besteht.
	 * Ansonsten passiert nichts.
	 */
	public void closeConnection() {
		if (connect == null) {
			return; // Verbindung wurde nicht erzeugt
		}
		try {
			connect.close();
		} catch (SQLException e) {
			System.err.println("couldn't close Database." + " Error: " + e.getMessage());
		}
	}

	/**
	 * Methode die für SQL-Abfragen, die ein Ergebnis zurückliefern bestimmt ist
	 * (z.B. Select)
	 * 
	 * @param sql
	 *            SQL-Abfrage
	 * @return Das ergebnis der Abfrage als Resultset.
	 */
	public ResultSet sqlQuery(String sql) {
		// long startTime = System.currentTimeMillis();
		try {
			data = command.executeQuery(sql);
		} catch (SQLException e) {
			System.err.println("Error: " + sql + "\r\n" + e.getMessage());
		}
		// System.out.println("sqlQuery duration: "+(System.currentTimeMillis()
		// - startTime));
		return data;
	}

	/**
	 * Methode zum ausführen von SQL-Befehlen bei denen kein Antwort zu erwarten
	 * ist
	 * 
	 * @param sql
	 *            SQL-Befehl
	 * @return Wahrheitswert ob der Befehl erfolgreich war.
	 */
	public boolean sqlCommand(String sql) {
		try {
			return command.execute(sql);
		} catch (SQLException e) {
			System.err.println("Error: " + sql + "\r\n" + e.getMessage());
			return false;
		}
	}

	/**
	 * Methode die die MetaDaten der Letzten abfrage zurückgibt.
	 * 
	 * @return Metadaten der Letzten SQL-Abfrage, wenn vorhanden ansonsten null.
	 */
	public ResultSetMetaData dbInfo() {
		if (data == null) {
			return null;
		}
		try {
			resm = data.getMetaData();
		} catch (SQLException e) {
			System.err.println("Error: " + e.getMessage());
		}
		return resm;
	}

	/**
	 * ???
	 */
	public void update() {
		try {
			data.updateRow();
		} catch (SQLException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	/**
	 * setter für den zugriff zum nachträglichen ändern
	 * 
	 * @param connUrl
	 *            neue zugriffsURL z.B.: "//localhost:3306/myDB"
	 */
	public void setConnUrl(String connUrl) {
		this.connUrl = connUrl;
	}

}
