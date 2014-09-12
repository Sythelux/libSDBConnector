package de.sydsoft.libsdb.datab;

import java.util.Properties;

/**
 * 
 * @author Sythelux Rikd
 */
public abstract class Constants {
	/** wahrheistwert ob sich das Programm im Debug-modus befindet */
	public static boolean DEBUG = false;
	/** Datenbankname */
	public static String DBNAME = "";
	/** Datenbanktyp z.B.: MySQL,Access */
	public static DBType DBTYP;
	/** Accountname */
	public static String DBKONTO = "";
	/** Passwort */
	public static String DBPW = "";
	/** Propertie für Access */
	public static Properties DBE = new Properties();
}
