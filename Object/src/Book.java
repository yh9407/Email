
public class Book {
	
	String publisher;
	String author;
	String title;
	//한번에 하려면 Book class library에 배열을 만들어서 배열을 넘김
	//필드 작성
	@Override
	public String toString() {
		return "Book [publisher=" + publisher +
				", author=" + author + ", title=" +
				title + "]";
	}
}
