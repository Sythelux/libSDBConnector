package de.sydsoft.libsdb.db;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * 
 * @author Sythelux Rikd
 */
public class DBMySQL extends DB {
	/** Benutzername */
	private String userName = "";
	/** Passwort */
	private String passwort = "";

	/** Standardkonstruktor */
	public DBMySQL() {
		super();
		init();
	}

	/**
	 * Konstruktor der eine Zeichenkette mit Dateinamen der Datenbank annimmt Bsp.: localhost:3306/meine_datenbank
	 * 
	 * @param dbName
	 *            Datenbankbezeichnung.
	 */
	public DBMySQL(String dbName) {
		super(dbName);
		init();
	}

	/**
	 * Konstruktor um Login zu optimieren
	 * 
	 * @param dbName
	 *            Datenbankbezeichnung.
	 * @param userName
	 *            Benutzername
	 * @param password
	 *            Passwort
	 */
	public DBMySQL(String dbName, String userName, String password) {
		super(dbName);
		this.userName = userName;
		this.passwort = password;
		init();
	}

	/**
	 * Konstruktor der eine Zeichenkette mit dem Pfad und Dateinamen der
	 * Datenbank annimmt.
	 * 
	 * @param dbName
	 *            Pfad zur Datei mit Dateinamen
	 * @param connProps
	 *            siehe Elternklasse: @DB
	 * @deprecated weil Properties von der verbindungsart nicht unterstützt
	 *             wird.
	 */
	@Deprecated
	public DBMySQL(String dbName, Properties connProps) {
		super(dbName, connProps);
	}

	private void init() {
		connUrl = "jdbc:mysql://";
		connUrlClose = "";
		driver = "com.mysql.jdbc.Driver";
	}

	/**
	 * baut eine Verbindung zu Datenbank auf.
	 * 
	 * @return Wahrheitswert ob das verbinden erfolgreich war
	 */
	@Override
	public boolean createConnection() {
		// long startTime = System.currentTimeMillis();
		boolean isconnected = false;
		try {
			if (connect == null || (connect != null && connect.isClosed())) {
				Class.forName(driver).newInstance();
				connect = DriverManager.getConnection(connUrl + dbName + connUrlClose, userName, passwort);
				command = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				isconnected = true;
			} else {
				isconnected = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.err.println("couldn't open Database: " + connUrl + dbName
			// + connUrlClose + " Error: " + e.getMessage());
		}
		// System.out.println("Connection opened duration: "+(System.currentTimeMillis()
		// - startTime)+" ms");
		return isconnected;
	}

	/**
	 * Setter für den Benutzernamen
	 * 
	 * @param userName
	 *            neuer Benutzername.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Setter für das Passwort
	 * 
	 * @param passwort
	 *            neues Passwort(unverschlüsselt)
	 */
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
}
