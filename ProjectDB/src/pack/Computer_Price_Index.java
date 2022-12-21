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
		/*
		Connection conn;
		Statement stmt = null;
		ResultSet srs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project_shopdb", "root", "qwer1234");
			System.out.println("DB connected");
			stmt = conn.createStatement();

			// stmt.executeUpdate("insert into student (id, name, dept)
			// values('0893012','아무개','컴퓨터공학');");// 쿼리문을 실행하여
			// printTable(stmt); // 결과를 넘겨준다.
			// 많이 쓰일듯.

			conn.close();
			System.out.println("DB disconnected");

		} catch (ClassNotFoundException e) {
			System.out.println("JDBC driver load error");
		} catch (SQLException e) {
			System.out.println("DB connect error");
		}*/

	}
}

