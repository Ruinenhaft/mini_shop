package pack;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// id, pw
		PDFrame PD;//제품파트를 가지고 UI, DB컨트롤을 수행하는 클래스입니다.
		AccountFrame AF;//계정파트를 가지고 UI, DB컨트롤을 수행하는 클래스입니다.
		SignFrame SF = new SignFrame();//로그인 클래스입니다.
		while(true) {
			if(SF.on == 2) {//로그인 클래스에서 ID와 PW가 일치하다면 다음 폼들을 호출합니다.
				PD = new PDFrame();
				AF = new AccountFrame();
				SF.dispose();
				break;
			}else {
				System.out.println("check admin id, pw");
			}
		}
	}
}

