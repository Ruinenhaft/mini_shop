package pack;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
	private JTextField soldout;// 매진이면 빨간색 바탕에 검정색 글씨로 매진 출력. 아니면 초록색 바탕.

	private JLabel count_lb;
	private JTextField count_output;

	// 정보
	private TextArea info;

	//이미지 경로
	private String img_loc = null;
	
	public MyFrame() {

		init();
		setDisplay();
		showDisply();
	}

	public void init() {
		search = new JLabel("제품이름을 입력하세요");
		tbxSearch = new JTextField(25);
		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new MyActionListener());
		category_lb = new JLabel("종류 :");
		category_output = new JTextField("DB값 입력");

		ImageIcon icon = new ImageIcon("image/R9-7950X.PNG");// 추후 디렉토리에서 직접 불러오는 파일로드? 찾아보기, DB에서 이미지 파일 이름 받아올것.
		product_img = new JLabel("No Image", icon, SwingConstants.CENTER);
		product_img.setPreferredSize(new Dimension(170, 170));// 사이즈 조절

		price_lb = new JLabel("가격 :");
		price_output = new JTextField("DB값 입력");
		saleRate_lb = new JLabel("할인률 :");
		saleRate_output = new JTextField("DB값 입력");
		sale_lb = new JLabel("할인가 :");
		sale_lb_output = new JTextField("DB값 입력");
		date_lb = new JLabel("출시일 :");
		date_output = new JTextField("DB값 입력");
		soldout = new JTextField("매진");
		count_lb = new JLabel("남은수량 :");
		count_output = new JTextField("DB값 입력");
		info = new TextArea("정보", 10, 10);
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
		pnlLayer4.add(soldout);
		JPanel pnlEast = new JPanel(new GridLayout());
		pnlEast.add(pnlLayer4, BorderLayout.EAST);

		JPanel pnlLayer5 = new JPanel(new GridLayout(1, 0));
		pnlLayer5.add(info);
		JPanel pnlSouth = new JPanel(new BorderLayout());
		pnlSouth.add(pnlLayer5, BorderLayout.SOUTH);

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlWest, BorderLayout.WEST);
		add(pnlEast, BorderLayout.EAST);
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
			throws SQLException {
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
			if (!col7.equals(""))
				img_loc = srs.getString("prod_image_src");//이미지 경로 처리.
			if (!col8.equals(""))
				count_output.setText(srs.getString("prod_count"));
			if (!col9.equals(""))
				soldout.setText(srs.getString("prod_soldout_yn"));
			if (!col10.equals(""))
				date_output.setText(srs.getString("prod_stocking_date"));
			//saleRate_output
		}
	}
	
	private class MyActionListener implements ActionListener {

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
				// printData(srs);
				// stmt.executeUpdate("insert into student (id, name, dept)
				// values('0893012','아무개','컴퓨터공학');");// 쿼리문을 실행하여
				// printTable(stmt); // 결과를 넘겨준다.
				// 많이 쓰일듯.

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
