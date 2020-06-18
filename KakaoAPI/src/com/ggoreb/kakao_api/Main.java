package com.ggoreb.kakao_api;

public class Main {
	public static void main(String[] args) {
		VisionUtil vu =new VisionUtil("37633581c3556b341f3e8e0d614e1b6c");
		String result = vu.sendFile("unnamed.png");
		System.out.println(result);

		
		
		
		
		
		
		
		// TranslateUtil tu = new TranslateUtil("37633581c3556b341f3e8e0d614e1b6c");
//		String result = 
//		tu.sendText("kr", "en", "");
//		System.out.println(result);

	}
}
