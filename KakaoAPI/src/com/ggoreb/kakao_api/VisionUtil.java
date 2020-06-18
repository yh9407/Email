package com.ggoreb.kakao_api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class VisionUtil {
	private String restAPIKey;

	public VisionUtil() {
	}

	public VisionUtil(String restApiKey) {
		this.restAPIKey = restApiKey;
	}

	public String sendFile(String imageFile) {
		String result = "";
		try {
			File uploadFile = new File(imageFile);
			String apiURL = "https://kapi.kakao.com/v1/vision/face/detect";
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setUseCaches(false);
			con.setDoOutput(true);
			con.setDoInput(true);
			// multipart request
			String boundary = "---" + System.currentTimeMillis() + "---";
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			con.setRequestProperty("Authorization", "KakaoAK " + restAPIKey);

			OutputStream outputStream = con.getOutputStream();
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
			String LINE_FEED = "\r\n";
			
			String fileName = uploadFile.getName();
			writer.append("--" + boundary).append(LINE_FEED);
			writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"")
					.append(LINE_FEED);
			writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
			writer.append(LINE_FEED);
			writer.flush();
			FileInputStream inputStream = new FileInputStream(uploadFile);
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.flush();
			inputStream.close();
			writer.append(LINE_FEED).flush();
			writer.append("--" + boundary + "--").append(LINE_FEED);
			writer.close();
			BufferedReader br = null;
			int responseCode = con.getResponseCode();
			if (responseCode == 200) { 
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // ?��?�� 발생
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

	public String sendUrl(String imageUrl) {
		String result = "";
		try {
			String apiURL = "https://kapi.kakao.com/v1/vision/face/detect";
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setUseCaches(false);
			con.setDoOutput(true);
			con.setDoInput(true);

			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty("Authorization", "KakaoAK " + restAPIKey);
			
			OutputStream outputStream = con.getOutputStream();
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
			writer.append("image_url=" + imageUrl);
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
