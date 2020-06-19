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

		// �ڹٽ�ũ��Ʈ �ڵ� ����
		JavascriptExecutor js = (JavascriptExecutor) driver;
		while (true) { // ��� �����͸� ������ ȭ�鿡 �����ֱ�

			long height = (long) js.executeScript("return document.body.scrollHeight");
			System.out.println(height);
			// 1. ���̾˾Ƴ�
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(950);
			// 'ajax'�� �����̶����� java ���ۿ��� �����̸� ��.

			// 2. ��ũ�� ����
			long height2 = (long) js.executeScript("return document.body.scrollHeight");
			System.out.println(height2);
			// 3. �ٽ� ���� �˾Ƴ�
			if (height == height2)
				break;
		}
		List<WebElement> list = driver.findElements(By.cssSelector(".img_area img")); // f12(�����ڸ��) ���� ���� �̾Ƴ����� �������� ���
		
		Test t = new Test(); //Test�� ������ Ŭ���� ��
		
		for (int i = 0; i < list.size(); i++) {
			WebElement el = list.get(i);
			String src = el.getAttribute("src");
			System.out.println(src);
			
			t.downloadImage(src, i ); //�Ʒ� �޼ҵ�(downloadImage)��
			//static�� �ƴϹǷ� ���θ޼ҵ忡�� ȣ���� �� ����( �޸𸮿� ����� �ȵǾ� �����ϱ�)
			//static �� ���̴��� new�� ����� ��Ű����.
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
