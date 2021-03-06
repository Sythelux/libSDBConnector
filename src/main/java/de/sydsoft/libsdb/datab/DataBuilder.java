/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package de.sydsoft.libsdb.datab;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractListModel;
import javax.swing.JTable;

import de.sydsoft.libsdb.db.DB;
import de.sydsoft.libsdb.db.DBAccess;
import de.sydsoft.libsdb.db.DBMSSQL;
import de.sydsoft.libsdb.db.DBMySQL;
import de.sydsoft.libsdb.db.DBSQLiteJDBC;

/**
 * 
 * @author Sythelux Rikd
 */
public class DataBuilder {
	private static Logger Log = Logger.getLogger(DataBuilder.class.getSimpleName());
	
	/** Object einer DB */
	protected DB db;
	/** tabelle mit der hauptsächlich gearbeitet wird */
	protected JTable tab;

	/** Standardkonstruktor */
	public DataBuilder() {
		switch (Constants.DBTYP) {
		case ACCESS:
			if (!Constants.DBE.isEmpty())
				this.db = new DBAccess(Constants.DBNAME, Constants.DBE);
			else
				this.db = new DBAccess(Constants.DBNAME);
			break;
		case MYSQL:
			this.db = new DBMySQL(Constants.DBNAME, Constants.DBKONTO, Constants.DBPW);
			break;
		case MSSQL:
			this.db = new DBMSSQL(Constants.DBNAME, Constants.DBKONTO, Constants.DBPW);
			break;
		case SQLITE:
			this.db = new DBSQLiteJDBC(Constants.DBNAME);
			break;
		}
		Log.config("an Instance of " + this.db.getClass() + " was created.");
	}

	/**
	 * Metode die einspaltig-erwartete SQL-befehle in ein eindimensionales Array
	 * umwandelt
	 * 
	 * @param SQLCom
	 *            SQL-Befehl der ein Ergebnis liefern soll.
	 * @return eindimensionales Zeichenkettenfeld
	 */
	public String[] buildSimpleData(String SQLCom) {
		if (mysqlActive()) {
			SQLCom = SQLCom.replace("Format", "Date_Format");
			SQLCom = SQLCom.replace("\'yyyy\'", "\'%Y\'");
		}
		Log.config("current Command: " + SQLCom);
		ResultSet data;
		String[] simpleData = { "Fehler" };
		ArrayList<String> hlp = new ArrayList<String>();
		String hlpStr = "";
		try {
			if (db != null) {
				long startTime = System.currentTimeMillis();
				if (db.createConnection()) {
					data = db.sqlQuery(SQLCom);
					while (data.next()) {
						hlpStr = data.getObject(1) + "";
						hlpStr = hlpStr.replace("null", "0");
						hlp.add(hlpStr);
					}
				}
				System.out.println("Abfragedauer: " + (System.currentTimeMillis() - startTime));
			}
		} catch (SQLException ex) {
			Log.log(Level.SEVERE, ex.getSQLState(), ex);
		}

		simpleData = hlp.toArray(simpleData);
		// db.closeConnection();
		return simpleData;
	}

	/**
	 * Metode die einspaltig-erwartete SQL-befehle in ein AbstractListModel
	 * umwandelt
	 * 
	 * @param SQLCom
	 * @return ein Model welches in Listboxen zum einsatz kommt
	 */
	public AbstractListModel<String> buildModel(String SQLCom) {
		final String[] simpleData = buildSimpleData(SQLCom);
		AbstractListModel<String> hlp = new AbstractListModel<String>() {
			private static final long serialVersionUID = 652295575070142477L;
			String[] strings = simpleData;

			@Override
			public int getSize() {
				return strings.length;
			}

			@Override
			public String getElementAt(int i) {
				return strings[i];
			}
		};
		return hlp;
	}

	/**
	 * Methode die prüft ob mysql eingestellt wurde
	 * 
	 * @return Wahrheitswert ob Access Datenbankzugriffe aktiv sind
	 */
	public boolean mysqlActive() {
		boolean act = false;
		if (Constants.DBTYP == DBType.MYSQL) {
			act = true;
		}
		return act;
	}

	/**
	 * Methode die prüft ob mysql eingestellt wurde
	 * 
	 * @return Wahrheitswert ob Access Datenbankzugriffe aktiv sind
	 */
	public boolean mssqlActive() {
		boolean act = false;
		if (Constants.DBTYP == DBType.MSSQL) {
			act = true;
		}
		return act;
	}

	/**
	 * Methode die prüft ob mysql eingestellt wurde
	 * 
	 * @return Wahrheitswert ob Access Datenbankzugriffe aktiv sind
	 */
	public boolean SQLiteActive() {
		boolean act = false;
		if (Constants.DBTYP == DBType.SQLITE) {
			act = true;
		}
		return act;
	}

	/**
	 * Methode die prüft ob access eingestellt wurde
	 * 
	 * @return Wahrheitswert ob Access Datenbankzugriffe aktiv sind
	 */
	public boolean accessActive() {
		boolean act = false;
		if (Constants.DBTYP == DBType.ACCESS) {
			act = true;
		}
		return act;
	}

	/**
	 * Getter für die Tabelle
	 * 
	 * @return
	 */
	public JTable getTab() {
		return tab;
	}
}
