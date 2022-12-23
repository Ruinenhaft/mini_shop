package pack;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;

import java.awt.event.*;
import java.sql.*;
public class SignFrame extends JFrame{
	private JLabel id;
	private JTextField id_TF;
	private JLabel pw;
	private JTextField pw_TF;
	private JButton btnLogin;
	public int on = 0;
	
	public SignFrame() {//생성자 클래스
		init();//초기화, 버튼이벤트 세팅메서드입니다.
		setDisplay();//UI배치 메서드
		showDisplay();//폼 세팅 메서드
	}
	
	public void init() {
		id = new JLabel("ID :");
		id_TF = new JTextField(15);
		pw = new JLabel("PW :");
		pw_TF = new JPasswordField(15);
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new btnLogin_func());
	}
	public void setDisplay() {
		JPanel pnlLayer1 = new JPanel(new GridLayout(3, 2));
		pnlLayer1.add(id);
		pnlLayer1.add(id_TF);
		pnlLayer1.add(pw);
		pnlLayer1.add(pw_TF);
		pnlLayer1.add(btnLogin);
		add(pnlLayer1, BorderLayout.CENTER);
	}
	
	public void showDisplay() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(200, 100);
		setResizable(false);// 사이즈 조절 불가
		setLocationRelativeTo(null);// 창이 가운데
		setVisible(true);
	}
	public int confirm_data(ResultSet srs, String col1, String col2) throws SQLException{//DB와의 값 대조후 result 값으로 성공여부 판별
		int result = 0;
		while (srs.next()) {
			if (!col1.equals("")) {
				String db_id = srs.getString("id_admin");
				String input_id = id_TF.getText();
				if(db_id.equals(input_id))
					result++;
			}
			if (!col2.equals("")) {
				String db_pw = srs.getString("pw_admin");
				String input_pw = id_TF.getText();
				if(db_pw.equals(input_pw))
					result++;
			}
		}
		if(result == 2)
			return result;
		else {
			System.out.println("check admin id, pw");
			return result;
		}
	}
	private class btnLogin_func implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Connection conn;
			Statement stmt = null;
			ResultSet srs = null;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project_shopdb", "root", "qwer1234");
				System.out.println("DB connected");
				stmt = conn.createStatement();

				String admin_id = id_TF.getText();//입력된 아이디
				String querry = String.format("select*from admin where id_admin = '%s'", admin_id);//아이디를 기반으로 select 쿼리문 작성
				System.out.println(querry);

				srs = stmt.executeQuery(querry);//쿼리문 결과 저장.
				
				on = confirm_data(srs, "id_admin", "pw_admin");//해당 결과물에서 대조하는 메서드 return int 타입 로그인 성공여부
				if(on == 2) {
					conn.close();
					System.out.println("DB disconnected");
				}else {
					System.out.println("fail:Login");
				}
				

			} catch (ClassNotFoundException e1) {
				System.out.println("JDBC driver load error");
			} catch (SQLException e1) {
				System.out.println("DB connect error");
			}
		}

	}
}
