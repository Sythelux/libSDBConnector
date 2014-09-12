/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.libsdb.datab;

/**
 * 
 * @author Sythelux Rikd
 */
public enum DBType {

	MYSQL("mySql"), MSSQL("msSql"), ACCESS("Access"), SQLITE("SQLite");

	String value;

	/**
	 * 
	 * @param value
	 */
	private DBType(String value) {
		this.value = value;
	}

}
