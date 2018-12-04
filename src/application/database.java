package application;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;

import javafx.scene.control.ChoiceBox;

public class database {

	static void createdbfolder() {
		new File("/D:/Clothes").mkdirs();
	}

	static String url = "jdbc:sqlite:D:/Clothes/cloth.db";
	static String sql = "SELECT id , name, phoneno ,clothtype , measurements ,fabric FROM customers";

	public static void createNewDatabase() {

		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("A new database has been created.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		createNewTable();
		createAdminTable();
	}

	public static void createNewTable() {
		// SQLite connection string
		System.out.println("Creating Table");
		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS customers (\n" + "	id integer PRIMARY KEY,\n"
				+ "	name text NOT NULL,\n" + "	phoneno real\n," + "clothtype text NOT NULL,\n"
				+ "	measurements text NOT NULL,\n" + "	now text NOT NULL,\n" + "datepk NOT NULL\n, "
				+ "fabric blob null" + ");";

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			// create a new table
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void createAdminTable() {

		String sql = "CREATE TABLE IF NOT EXISTS admins (\n" + "	" + "id integer PRIMARY KEY,\n"
				+ "	name text NOT NULL,\n" + "	password text NOT NULL\n" + ");";
		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			// create a new table
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public static void addadmin(String name, String pass) {

		String sql = "INSERT INTO admins( name,password ) VALUES(?,?)";

		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, name);
			pstmt.setString(2, pass);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void delete(int id) {
		String sql = "DELETE FROM customers WHERE id = ?";

		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// set the corresponding param
			pstmt.setInt(1, id);
			// execute the delete statement
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void insert(String name, String phoneno,  String clothtype, String measurements,
			String pick, byte[] fabric) {
		String sql = "INSERT INTO customers(name, phoneno ,clothtype , measurements, now , datepk,fabric) VALUES(?,?,?,?,?,?,?)";

		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, name);
			pstmt.setString(2, phoneno);
			pstmt.setString(3, clothtype);
			pstmt.setString(4, measurements);
			pstmt.setString(5, makedate());
			pstmt.setString(6, pick);
			pstmt.setBytes(7, fabric);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static String makedate() {

		Date date = Calendar.getInstance(TimeZone.getTimeZone("Africa/Nairobi")).getTime();
		DateFormat dateFormat = new SimpleDateFormat("MMMM dd ,yyyy ");
		return dateFormat.format(date);
	}
}
