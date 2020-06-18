import java.io.IOException;

public class 브라우저띄우기 {
	public static void main(String[] args) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		
		runtime.exec("explorer.exe http://www.naver.com");
	}
}
