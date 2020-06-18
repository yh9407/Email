package email;


public class EmailMain {
	public static void main(String[] args) {
		String to = "dudgh9407@naver.com";
		String subject = "반갑습니다.";
		String content = "가입을 축하드립니다.";
		SMTPAuthenticator smtp = new SMTPAuthenticator();
		Mailer mailer = new Mailer();
		mailer.sendMail(to, subject, content, smtp);
	}
}