package selenium;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Test {
	public static void main(String[] args) throws InterruptedException {
		
		WebDriver driver = new ChromeDriver();
		driver.get("https://search.naver.com/search.naver?where=image&sm=tab_jum&query=%EC%82%B5");

		// 자바스크립트 코드 실행
		JavascriptExecutor js = (JavascriptExecutor) driver;
		while (true) { // 모든 데이터를 브라우저 화면에 보여주기

			long height = (long) js.executeScript("return document.body.scrollHeight");
			System.out.println(height);
			// 1. 높이알아냄
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(950);
			// 'ajax'의 딜레이때문에 java 동작에도 딜레이를 줌.

			// 2. 스크롤 내림
			long height2 = (long) js.executeScript("return document.body.scrollHeight");
			System.out.println(height2);
			// 3. 다시 높이 알아냄
			if (height == height2)
				break;
		}
		List<WebElement> list = driver.findElements(By.cssSelector(".img_area img")); // f12(개발자모드) 에서 내가 뽑아내려는 데이터의 경로
		
		Test t = new Test(); //Test인 이유는 클래스 명
		
		for (int i = 0; i < list.size(); i++) {
			WebElement el = list.get(i);
			String src = el.getAttribute("src");
			System.out.println(src);
			
			t.downloadImage(src, i ); //아래 메소드(downloadImage)가
			//static이 아니므로 메인메소드에서 호출할 수 없음( 메모리에 등록이 안되어 있으니까)
			//static 을 붙이던지 new로 등록을 시키던지.
		}

		System.out.println("==================END====================");

	}

	public void downloadImage(String address, int i) {
		try {
			URL imageURL = new URL(address);
			BufferedImage saveImage = ImageIO.read(imageURL);

			ImageIO.write(saveImage, "jpg", new File(i + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
