
public class Main {
	public static void main(String[] args) {
		// Q2.//Book 클래스 선언 및 초기화
		Book book = new Book();
		Book book1 = new Book();
		Book book2 = new Book();

		// 데이터 입력
		book.publisher = "창비";
		book1.publisher = "더좋은책";
		book2.publisher = "한빛비즈";

		book.author = "황석영";
		book1.author = "와다 히데키";
		book2.author = "이한나";

		book.title = "철도원 삼대";
		book1.title = "감정바보";
		book2.title = "할짝 심리학";

		System.out.println(book);
		System.out.println(book1);
		System.out.println(book2);

		// Q1.
		Member member = new Member();

		member.id = "abcde";
		member.password = "12345";
		member.name = "홍길동";
		member.age = 20;
		member.height = 178.3;

		System.out.println(member);
	}
}
