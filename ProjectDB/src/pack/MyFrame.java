package pack;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class MyFrame extends JFrame {

	// 최상단 부터 한단계씩 아래로
	// 검색
	private JLabel search;
	private JTextField tbxSearch;
	private JButton btnSearch;

	// 범주
	private JLabel category_lb;
	private JTextField category_output;

	// 이미지
	private JLabel product_img;

	// 가격, 할인율, 할인가, 생산일
	private JLabel price_lb;
	private JTextField price_output;

	private JLabel saleRate_lb;
	private JTextField saleRate_output;// DB에 없고 계산해서 넣어야함.

	private JLabel sale_lb;
	private JTextField sale_lb_output;

	private JLabel date_lb;
	private JTextField date_output;

	// 매진여부(체크박스), 남은수량
	private JLabel soldout_lb;
	private JTextField soldout;// 매진이면 빨간색 바탕에 검정색 글씨로 매진 출력. 아니면 초록색 바탕.

	private JLabel count_lb;
	private JTextField count_output;

	// 정보
	private TextArea info;
	
	//하단 버튼
	private JButton btnDrop;
	private JButton btnAdd;
	private JButton btnModify;

	//이미지 경로
	private String img_loc = "image/No_Image.png";
	
	public MyFrame() {

		init();
		setDisplay();
		showDisply();
	}

	public void init(){
		search = new JLabel("제품이름을 입력하세요");
		tbxSearch = new JTextField(25);
		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new btnSearch_func());
		category_lb = new JLabel("종류 :");
		category_output = new JTextField("DB값 입력");

		ImageIcon icon = new ImageIcon(img_loc);// 추후 디렉토리에서 직접 불러오는 파일로드? 찾아보기, DB에서 이미지 파일 이름 받아올것.
		product_img = new JLabel(icon);

		price_lb = new JLabel("가격 :");
		price_output = new JTextField("DB값 입력");
		saleRate_lb = new JLabel("할인률 :");
		saleRate_output = new JTextField("0");
		sale_lb = new JLabel("할인가 :");
		sale_lb_output = new JTextField("DB값 입력");
		date_lb = new JLabel("출시일 :");
		date_output = new JTextField("DB값 입력");
		soldout_lb = new JLabel("매진여부 :");
		soldout = new JTextField("매진");
		count_lb = new JLabel("남은수량 :");
		count_output = new JTextField("DB값 입력");
		btnDrop = new JButton("삭제");
		btnDrop.addActionListener(new btnDrop_func());
		btnAdd = new JButton("추가");
		btnAdd.addActionListener(new btnAdd_func());
		btnModify = new JButton("수정");
		btnModify.addActionListener(new btnModify_func());
		info = new TextArea("정보", 10, 10);
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

				String name = tbxSearch.getText();
				String querry = String.format("UPDATE product_info_tbl SET prod_kind = ?, prod_price =?, prod_sale_price=?, prod_coment=?, prod_image_src=?, prod_count=?, prod_soldout_yn=?, prod_stocking_date=?"
						+ " WHERE prod_name = '%s';", name);
				
				PreparedStatement pstmt = null;
				try {
					pstmt = conn.prepareStatement(querry);
					pstmt.setString(1, category_output.getText());
					pstmt.setString(2, price_output.getText());
					pstmt.setString(3, sale_lb_output.getText());
					pstmt.setString(4, info.getText());
					pstmt.setString(5, null);//이미지 경로.
					pstmt.setString(6, count_output.getText());
					pstmt.setString(7, soldout.getText());
					pstmt.setString(8, date_output.getText());
					
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

				String name = tbxSearch.getText();
				String querry = String.format("DELETE FROM product_info_tbl WHERE prod_name = '%s'", name);

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

				String querry = "INSERT INTO product_info_tbl (prod_id, prod_name, prod_kind, prod_price, prod_sale_price, prod_coment, prod_image_src, prod_count, prod_soldout_yn, prod_stocking_date) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
				PreparedStatement pstmt = null;
				try {
					pstmt = conn.prepareStatement(querry);
					pstmt.setString(1, null);
					pstmt.setString(2, tbxSearch.getText());
					pstmt.setString(3, category_output.getText());
					pstmt.setString(4, price_output.getText());
					pstmt.setString(5, sale_lb_output.getText());
					pstmt.setString(6, info.getText());
					pstmt.setString(7, null);//이미지 경로.
					pstmt.setString(8, count_output.getText());
					pstmt.setString(9, soldout.getText());
					pstmt.setString(10, date_output.getText());
					
				}catch(Exception e1) {
					System.out.println("DB fail:ADD");
				}
				System.out.println(querry);

				result = pstmt.executeUpdate();
				if(result == 1) {
					System.out.println("add DB Complete");
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
	public void setDisplay() {
		JPanel pnlLayer1 = new JPanel(new GridLayout(1, 3));
		pnlLayer1.add(search);
		pnlLayer1.add(tbxSearch);
		pnlLayer1.add(btnSearch);
		JPanel pnlNorth = new JPanel(new BorderLayout());
		pnlNorth.add(pnlLayer1, BorderLayout.NORTH);

		JPanel pnlLayer3 = new JPanel(new GridLayout(1, 0));
		pnlLayer3.add(product_img);
		JPanel pnlWest = new JPanel(new BorderLayout());
		pnlWest.add(pnlLayer3, BorderLayout.WEST);

		JPanel pnlLayer4 = new JPanel(new GridLayout(9, 2));
		pnlLayer4.add(category_lb);
		pnlLayer4.add(category_output);
		pnlLayer4.add(price_lb);
		pnlLayer4.add(price_output);
		pnlLayer4.add(saleRate_lb);
		pnlLayer4.add(saleRate_output);
		pnlLayer4.add(sale_lb);
		pnlLayer4.add(sale_lb_output);
		pnlLayer4.add(date_lb);
		pnlLayer4.add(date_output);
		pnlLayer4.add(count_lb);
		pnlLayer4.add(count_output);
		pnlLayer4.add(soldout_lb);
		pnlLayer4.add(soldout);
		JPanel pnlEast = new JPanel(new GridLayout());
		pnlEast.add(pnlLayer4, BorderLayout.EAST);

		JPanel pnlLayer5 = new JPanel(new GridLayout(1, 0));
		pnlLayer5.add(info);
		JPanel pnlLayer6 = new JPanel(new GridLayout(1,2));
		pnlLayer6.add(btnAdd);
		pnlLayer6.add(btnModify);
		pnlLayer6.add(btnDrop);
		
		JPanel pnlSouth = new JPanel(new BorderLayout());
		pnlSouth.add(pnlLayer5, BorderLayout.SOUTH);
		pnlSouth.add(pnlLayer6, BorderLayout.NORTH);

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlWest, BorderLayout.WEST);
		add(pnlEast, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
	}

	public void showDisply() {
		setTitle("GridLayout Sample");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 600);
		setResizable(false);// 사이즈 조절 불가
		setLocationRelativeTo(null);// 창이 가운데
		setVisible(true);
	}

	private void printData(ResultSet srs, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10)
			throws SQLException{
		while (srs.next()) {
			if (!col1.equals(""))
				System.out.print(srs.getString("prod_id"));
			if (!col2.equals(""))
				System.out.println("\t|\t" + srs.getString("prod_name"));
			if (!col3.equals(""))
				category_output.setText(srs.getString("prod_kind"));
			if (!col4.equals(""))
				price_output.setText(srs.getString("prod_price"));
			if (!col5.equals(""))
				sale_lb_output.setText(srs.getString("prod_sale_price"));
			if (!col6.equals(""))
				info.setText(srs.getString("prod_coment"));
			if (!col7.equals("")) {
				img_loc = String.format("image/%s",srs.getString("prod_image_src"));
				System.out.println(img_loc);
				product_img.setIcon(new ImageIcon(img_loc));
			}
			if (!col8.equals(""))
				count_output.setText(srs.getString("prod_count"));
			if (!col9.equals(""))
				soldout.setText(srs.getString("prod_soldout_yn"));
			if (!col10.equals(""))
				date_output.setText(srs.getString("prod_stocking_date"));
			//saleRate_output
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

				String name = tbxSearch.getText();
				String querry = String.format("select*from product_info_tbl where prod_name = '%s'", name);
				System.out.println(querry);

				srs = stmt.executeQuery(querry);
				
				printData(srs, "prod_id", "prod_name", "prod_kind", "prod_price", "prod_sale_price",
							"prod_coment","prod_image_src","prod_count","prod_soldout_yn","prod_stocking_date");


				conn.close();
				System.out.println("DB disconnected");

			} catch (ClassNotFoundException e1) {
				System.out.println("JDBC driver load error");
			} catch (SQLException e1) {
				System.out.println("DB connect error");
			}
		}

	}
}
