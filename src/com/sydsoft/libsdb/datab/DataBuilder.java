/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.sydsoft.libsdb.datab;

import com.sydsoft.libsdb.db.KeyWords;
import com.sydsoft.libsdb.db.DBAccess;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.JTable;
import com.sydsoft.libsdb.db.DB;
import com.sydsoft.libsdb.db.DBMSSQL;
import com.sydsoft.libsdb.db.DBMySQL;
import com.sydsoft.libsdb.db.DBSQLiteJDBC;
import com.sfl.Message;


/**
 * 
 * @author Sythelux Rikd
 */
public class DataBuilder implements KeyWords {

    /** Object einer DB */
    protected DB db;
    /** tabelle mit der hauptsächlich gearbeitet wird*/
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
        Message.dbgMsg("eine Instanz von " + this.db.getClass() + " wurde erstellt.");
    }

    /**
     * Metode die einspaltig-erwartete SQL-befehle in ein eindimensionales Array umwandelt
     * @param SQLCom SQL-Befehl der ein Ergebnis liefern soll.
     * @return  eindimensionales Zeichenkettenfeld
     */
    public String[] buildSimpleData(String SQLCom) {
        if (mysqlActive()) {
            SQLCom = SQLCom.replace("Format", "Date_Format");
            SQLCom = SQLCom.replace("\'yyyy\'", "\'%Y\'");
        }
        Message.dbgMsg("aktuelles Kommando: "+SQLCom);
        ResultSet data;
        String[] simpleData = {"Fehler"};
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
                System.out.println("Abfragedauer: "+(System.currentTimeMillis() - startTime));
            }
        } catch (SQLException ex) {
            Message.errMsg(ex.getMessage());
        }
        
        simpleData = hlp.toArray(simpleData);
        //db.closeConnection();
        return simpleData;
    }

    /**
     * Metode die einspaltig-erwartete SQL-befehle in ein AbstractListModel umwandelt 
     * @param SQLCom
     * @return ein Model welches in Listboxen zum einsatz kommt
     */
    public AbstractListModel buildModel(String SQLCom) {
        final String[] simpleData = buildSimpleData(SQLCom);
        AbstractListModel hlp = new AbstractListModel() {
            
            String[] strings = simpleData;
            
            public int getSize() {
                return strings.length;
            }
            
            public Object getElementAt(int i) {
                return strings[i];
            }
        };
        return hlp;
    }


   
    /**
     * Methode die prüft ob mysql eingestellt wurde
     * @return Wahrheitswert ob Access Datenbankzugriffe aktiv sind
     */
    public boolean mysqlActive(){
        boolean act = false;
        if (Constants.DBTYP == DBTyp.MYSQL) {
            act = true;
        }
        return act;
    }
    
    /**
     * Methode die prüft ob mysql eingestellt wurde
     * @return Wahrheitswert ob Access Datenbankzugriffe aktiv sind
     */
    public boolean mssqlActive(){
        boolean act = false;
        if (Constants.DBTYP == DBTyp.MSSQL) {
            act = true;
        }
        return act;
    }
    /**
     * Methode die prüft ob mysql eingestellt wurde
     * @return Wahrheitswert ob Access Datenbankzugriffe aktiv sind
     */
    public boolean SQLiteActive(){
        boolean act = false;
        if (Constants.DBTYP == DBTyp.SQLITE) {
            act = true;
        }
        return act;
    }
    /**
     * Methode die prüft ob access eingestellt wurde
     * @return Wahrheitswert ob Access Datenbankzugriffe aktiv sind
     */
    public boolean accessActive(){
        boolean act = false;
        if (Constants.DBTYP == DBTyp.ACCESS) {
            act = true;
        }
        return act;
    }
    
    /**
     * Getter für die Tabelle
     * @return 
     */
    public JTable getTab() {
        return tab;
    }
}
