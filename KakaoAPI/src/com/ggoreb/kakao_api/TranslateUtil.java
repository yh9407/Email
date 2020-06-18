package com.ggoreb.kakao_api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class TranslateUtil {
	private String restAPIKey;

	public TranslateUtil() {
	}

	public TranslateUtil(String restApiKey) {
		this.restAPIKey = restApiKey;
	}

	public String sendText(String source, String target, String query) {
		String result = "";
		try {
			String apiURL = "https://kapi.kakao.com/v1/translation/translate"; //카카오 페이지에 있는 URL 
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection(); //Host
			con.setUseCaches(false);
			con.setDoOutput(true);
			con.setDoInput(true);

			con.setRequestProperty("Authorization", "KakaoAK " + restAPIKey); //Authorization
			
			OutputStream outputStream = con.getOutputStream();
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
			writer.append("src_lang=" + source);    //parameter
			writer.append("&target_lang=" + target);
			writer.append("&query=" + query);
			writer.flush();
			outputStream.flush();
			writer.close();
			BufferedReader br = null;
			int responseCode = con.getResponseCode();
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				System.out.println("error!!!!!!! responseCode= " + responseCode);
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			}
			String inputLine;
			if (br != null) {
				StringBuffer response = new StringBuffer();
				while ((inputLine = br.readLine()) != null) {
					response.append(inputLine);
				}
				br.close();
				result = response.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}
		
		return result;
	}
}
