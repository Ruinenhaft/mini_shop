package pack;

import java.sql.*;


public class Computer_Price_Index {
	private static void printData(ResultSet srs, String col1, String col2, String col3) throws SQLException {
		while (srs.next()) {
			if (!col1.equals(""))
				System.out.print(srs.getString("id"));
			if (!col2.equals(""))
				System.out.print("\t|\t" + srs.getString("name"));
			if (!col3.equals(""))
				System.out.println("\t|\t" + srs.getString("dept"));
			else
				System.out.println();
		}
	}

	private static void printTable(Statement stmt) throws SQLException {
		ResultSet srs = stmt.executeQuery("select * from student");
		while (srs.next()) {
			System.out.print(srs.getString("id"));
			System.out.print("\t|\t" + srs.getString("name"));
			System.out.println("\t|\t" + srs.getString("dept"));
		}
		System.out.println();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyFrame mf = new MyFrame();

	}
}

