//package web;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//public class Selector {
//	public static void main(String[] args) {
//	//	Document doc = Jsoup.connect( "https://www.w3schools.com/css/css_selectors.asp");
//		Elements els = doc.select("#leftmenuinner > a > font > font");
//		for (int i = 0; i < els.size(); i++) {
//			Element el = els.get(i);
//			String num = el.text();
//			System.out.println(num);
//
//		}
//
//	}
//}