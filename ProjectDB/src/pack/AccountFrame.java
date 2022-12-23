package pack;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class AccountFrame extends JFrame{
	private JButton btnSearch;
	private JLabel lb_user_id;
	private JTextField tf_user_id;
	
	private JLabel lb_id;
	private JTextField tf_id;

	private JLabel lb_pw;
	private JTextField tf_pw;
	
	private JLabel lb_user_addr;
	private JTextField tf_user_addr;

	private JLabel lb_user_phone;
	private JTextField tf_user_phone;

	private JLabel lb_user_class;
	private JTextField tf_user_class;
	
	//하단 버튼
	private JButton btnDrop;
	private JButton btnAdd;
	private JButton btnModify;
	private void printData(ResultSet srs, String col1, String col2, String col3, String col4, String col5, String col6)
			throws SQLException{
		while (srs.next()) {
			if (!col1.equals(""))
				tf_id.setText(srs.getString("id"));
			if (!col2.equals(""))
				System.out.println("\t|\t" + srs.getString("user_id"));
			if (!col3.equals(""))
				tf_pw.setText(srs.getString("user_pw"));
			if (!col4.equals(""))
				tf_user_addr.setText(srs.getString("user_addr"));
			if (!col5.equals(""))
				tf_user_phone.setText(srs.getString("user_phone"));
			if (!col6.equals(""))
				tf_user_class.setText(srs.getString("user_class"));
		}
	}

	private class btnAdd_func implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Connection conn;
			Statement stmt = null;
			int result = 0;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project_shopdb", "root", "qwer1234");
				System.out.println("DB connected");
				stmt = conn.createStatement();

				String querry = "INSERT INTO user_info_tbl (id, user_id, user_pw, user_addr, user_phone, user_class) "
						+ "VALUES (?, ?, ?, ?, ?, ?);";
				PreparedStatement pstmt = null;
				try {
					pstmt = conn.prepareStatement(querry);
					pstmt.setString(1, null);
					pstmt.setString(2, tf_user_id.getText());
					pstmt.setString(3, tf_pw.getText());
					pstmt.setString(4, tf_user_addr.getText());
					pstmt.setString(5, tf_user_phone.getText());
					pstmt.setString(6, tf_user_class.getText());
					
				}catch(Exception e1) {
					System.out.println("DB fail:user ADD");
				}
				System.out.println(querry);

				result = pstmt.executeUpdate();
				if(result == 1) {
					System.out.println("add DB_user Complete");
				}

				conn.close();
				System.out.println("DB_user disconnected");

			} catch (ClassNotFoundException e1) {
				System.out.println("JDBC driver load error");
			} catch (SQLException e1) {
				System.out.println("DB connect error");
			}
		}

	}
	private class btnSearch_func implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Connection conn;
			Statement stmt = null;
			ResultSet srs = null;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project_shopdb", "root", "qwer1234");
				System.out.println("DB connected");
				stmt = conn.createStatement();

				String id = tf_user_id.getText();
				String querry = String.format("select*from user_info_tbl where user_id = '%s'", id);
				System.out.println(querry);

				srs = stmt.executeQuery(querry);
				
				printData(srs, "id", "user_id", "user_pw", "user_addr", "user_phone", "user_class");


				conn.close();
				System.out.println("DB disconnected");

			} catch (ClassNotFoundException e1) {
				System.out.println("JDBC driver load error");
			} catch (SQLException e1) {
				System.out.println("DB connect error");
			}
		}

	}
	private class btnDrop_func implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Connection conn;
			Statement stmt = null;
			int result = 0;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project_shopdb", "root", "qwer1234");
				System.out.println("DB connected");
				stmt = conn.createStatement();

				String name = tf_user_id.getText();
				String querry = String.format("DELETE FROM user_info_tbl WHERE user_id = '%s'", name);

				System.out.println(querry);
				result = stmt.executeUpdate(querry);
				if(result == 1) {
					System.out.println("DELETE DB Complete");
				}

				conn.close();
				System.out.println("DB disconnected");

			} catch (ClassNotFoundException e1) {
				System.out.println("JDBC driver load error");
			} catch (SQLException e1) {
				System.out.println("DB connect error");
			}
		}

	}
	
	private class btnModify_func implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Connection conn;
			Statement stmt = null;
			int result = 0;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project_shopdb", "root", "qwer1234");
				System.out.println("DB connected");
				stmt = conn.createStatement();

				String name = tf_user_id.getText();
				String querry = String.format("UPDATE user_info_tbl SET id = ?, user_pw =?, user_addr=?, user_phone=?, user_class=?"
						+ " WHERE user_id = '%s';", name);
				
				PreparedStatement pstmt = null;
				try {
					pstmt = conn.prepareStatement(querry);
					pstmt.setString(1, tf_id.getText());
					pstmt.setString(2, tf_pw.getText());
					pstmt.setString(3, tf_user_addr.getText());
					pstmt.setString(4, tf_user_phone.getText());
					pstmt.setString(5, tf_user_class.getText());
					
				}catch(Exception e1) {
					System.out.println("DB fail:UPDATE");
				}
				System.out.println(querry);

				result = pstmt.executeUpdate();
				if(result == 1) {
					System.out.println("Update DB Complete");
				}

				conn.close();
				System.out.println("DB disconnected");

			} catch (ClassNotFoundException e1) {
				System.out.println("JDBC driver load error");
			} catch (SQLException e1) {
				System.out.println("DB connect error");
			}
		}

	}
	public void init(){
		
		lb_user_id = new JLabel("유저 아이디를 입력해주세요");
		tf_user_id = new JTextField(25);
		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new btnSearch_func());
		
		lb_id = new JLabel("DB id :");
		tf_id = new JTextField("-");
		
		lb_pw = new JLabel("pw :");
		tf_pw = new JTextField("-");
		
		lb_user_addr = new JLabel("주소 :");
		tf_user_addr = new JTextField("-");

		lb_user_phone = new JLabel("번호 :");
		tf_user_phone = new JTextField("0");
		
		lb_user_class = new JLabel("등급 :");
		tf_user_class = new JTextField("0");
		
		btnDrop = new JButton("삭제");
		btnDrop.addActionListener(new btnDrop_func());
		btnAdd = new JButton("추가");
		btnAdd.addActionListener(new btnAdd_func());
		btnModify = new JButton("수정");
		btnModify.addActionListener(new btnModify_func());
		
	}
	
	public void setDisplay() {
		JPanel pnlLayer1 = new JPanel(new GridLayout(1, 3));
		pnlLayer1.add(lb_user_id);
		pnlLayer1.add(tf_user_id);
		pnlLayer1.add(btnSearch);
		JPanel pnlNorth = new JPanel(new BorderLayout());
		pnlNorth.add(pnlLayer1, BorderLayout.NORTH);

		JPanel pnlLayer2 = new JPanel(new GridLayout(5, 2));
		pnlLayer2.add(lb_id);
		pnlLayer2.add(tf_id);
		pnlLayer2.add(lb_pw);
		pnlLayer2.add(tf_pw);
		pnlLayer2.add(lb_user_addr);
		pnlLayer2.add(tf_user_addr);
		pnlLayer2.add(lb_user_phone);
		pnlLayer2.add(tf_user_phone);
		pnlLayer2.add(lb_user_class);
		pnlLayer2.add(tf_user_class);
		JPanel pnlEast = new JPanel(new BorderLayout());
		pnlEast.add(pnlLayer2, BorderLayout.CENTER);
		
		JPanel pnlLayer3 = new JPanel(new GridLayout(1,3));
		pnlLayer3.add(btnAdd);
		pnlLayer3.add(btnModify);
		pnlLayer3.add(btnDrop);
		JPanel pnlSouth = new JPanel(new BorderLayout());
		pnlSouth.add(pnlLayer3, BorderLayout.SOUTH);

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlEast, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
	}
	public void showDisply() {
		setTitle("user info");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 200);
		setResizable(false);// 사이즈 조절 불가
		setLocationRelativeTo(null);// 창이 가운데
		setVisible(true);
	}
	public AccountFrame() {
		init();//초기화 메서드
		setDisplay();//UI, DB 관리 메서드
		showDisply();//폼 세팅 메서드
	}
}
