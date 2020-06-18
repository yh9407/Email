import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parsing {
	public static void main(String[] args) throws IOException {
		
		Document doc = Jsoup.connect("https://comic.naver.com/webtoon/list.nhn?titleId=602910&weekday=mon").get();
		String title = doc.title();
		System.out.println(title);
		
		Elements es = doc.select("td.title > a"); //title 에서 a가 포함된 모든 걸 가져온다 select에 마우스올리면 리턴값 알 수 있음.
		for (int i = 0; i < es.size(); i++) { //length 없다면 size로 대체
			Element e = es.get(i);//한개짜리 element로 나옴 
			
			String s = e.text();  //Element 'e' 를 text로 s라는 변수에 넣음
			System.out.println(s); //제목
			
			System.out.println();
			
			String href = e.attr("href"); //주소
			System.out.println("주소창  = " + href);
		
		}
	}
}
