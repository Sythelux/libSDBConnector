package com.sydsoft.libsdb.db;

import java.util.Properties;

/**
 * 
 * @author Sythelux Rikd
 */
public class DBSQLiteJDBC extends DB {
    /** Standardkonstruktor */
    public DBSQLiteJDBC() {
        super();
        init();
    }

    /**
     * Konstruktor der eine Zeichenkette mit dem Pfad und Dateinamen der Datenbank annimmt
     * @param dbName Pfad zur Datei mit Dateinamen
     */
    public DBSQLiteJDBC(String dbName) {
        super(dbName);
        connProps = new Properties();
        connProps.put("charSet", "Latin1");
        init();
    }
    
    public DBSQLiteJDBC(String dbName, Properties props) {
        super(dbName, props);
        init();
    }
    
    private void init(){
        connUrl = "jdbc:sqlite:";
        //connUrlClose = "";
        driver = "org.jdbc.sqlite";
    }
}