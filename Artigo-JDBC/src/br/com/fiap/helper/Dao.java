package br.com.fiap.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Dao {

	protected Connection connection;
	protected PreparedStatement stmt;
	protected ResultSet rs;

	private String url = 
			"jdbc:mysql://localhost:3306/DBSeguro?autoReconnect=true&useSSL=false";

	protected void abrirConexao() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		
		this.connection = DriverManager.getConnection(this.url, "root", "root");
	}
	
	protected void closePrepareResult() {
		if (this.rs != null) {
			try {
				this.rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (this.stmt != null) {
			try {
				this.stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void close() {
		if (this.rs != null) {
			try {
				this.rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (this.stmt != null) {
			try {
				this.stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (this.connection != null) {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}