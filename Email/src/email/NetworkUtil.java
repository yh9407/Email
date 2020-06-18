package email;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class NetworkUtil {
	public String get(String address) {
		StringBuffer result = new StringBuffer();
		try {
			URL url = new URL(address);
			URLConnection con = url.openConnection();

			InputStream is = con.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader reader = new BufferedReader(isr);

			String separator = "";

			while (true) {
				String data = reader.readLine();
				if (data == null) {
					break;
				}
				result.append(separator + data);
				separator = "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public String getKakao(String address, String appKey) {
		StringBuffer result = new StringBuffer();
		try {
			URL url = new URL(address);
			URLConnection con = url.openConnection();
			con.addRequestProperty("Authorization", "KakaoAK " + appKey);

			InputStream is = con.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader reader = new BufferedReader(isr);

			String separator = "";
			while (true) {
				String data = reader.readLine();
				if (data == null) {
					break;
				}
				result.append(separator + data);
				separator = "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public String postNaver(String address, String param, String clientId, String clientSecret) {

		StringBuffer result = new StringBuffer();

		try {
			URL url = new URL(address);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection con = (HttpURLConnection) urlConnection;

			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

			con.setRequestMethod("POST");
			con.setDoOutput(true);

			OutputStream out = con.getOutputStream();
			OutputStreamWriter osr = new OutputStreamWriter(out, "utf-8");
			BufferedWriter writer = new BufferedWriter(osr);
			writer.write(param);

			writer.close();
			osr.close();
			out.close();

			InputStream is = con.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader reader = new BufferedReader(isr);

			String separator = "";

			while (true) {
				String data = reader.readLine();
				if (data == null) {
					break;
				}
				result.append(separator + data);
				separator = "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.toString();

	}

}
