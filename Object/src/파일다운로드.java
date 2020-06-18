import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class 파일다운로드 {
	public static void main(String[] args) throws IOException {
		파일다운로드 f = new 파일다운로드();
		for (int i = 1; i <= 20; i++) {
			f.saveImage(
					"http://subcontentsdnak.ollehslp.com//0014533/01.mp4", 
					"0" + i + ".mp4");			
		}
	}

	public void saveImage(String strUrl, String savePath) throws IOException {

		URL url = null;
		InputStream in = null;
		OutputStream out = null;

		try {

			url = new URL(strUrl);

			in = url.openStream();

			out = new FileOutputStream(savePath); // 저장경로

			while (true) {
				// 이미지를 읽어온다.
				int data = in.read();
				if (data == -1) {
					break;
				}
				// 이미지를 쓴다.
				out.write(data);

			}

			in.close();
			out.close();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}

		}
	}
}
