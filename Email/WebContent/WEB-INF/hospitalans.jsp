
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="email.NetworkUtil"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="application/json; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%
String area = request.getParameter("area");
if(area == null) area = "";

Class.forName("com.mysql.jdbc.Driver");

String url = "jdbc:mysql://localhost:3306/java";
String id = "root";
String pw = "1234";
Connection con = 
		DriverManager.getConnection(url, id, pw);

StringBuffer query = new StringBuffer();
query.append("SELECT ID, SIDO, NAME");
query.append("     , MEDICAL, ROOM, TEL, ADDRESS");
query.append("  FROM HOSPITAL");
query.append(" WHERE ADDRESS LIKE CONCAT('%', ?, '%')"); // sql 문장

PreparedStatement stmt = con.prepareStatement(query.toString());
stmt.setString(1, area);

ResultSet rs = stmt.executeQuery();

List<Map<String, String>> list = new ArrayList<>();

while(rs.next()) {
	
	Map<String, String> map = new HashMap<>();
	
	int id2 = rs.getInt("ID");
	String sido = rs.getString("SIDO");
	String name = rs.getString("NAME");
	String medical = rs.getString("MEDICAL");
	String room = rs.getString("ROOM");
	String tel = rs.getString("TEL");
	String address = rs.getString("ADDRESS");
	
	map.put("id", id2 + "");
	map.put("sido", sido);
	map.put("name", name);
	map.put("medical", medical);
	map.put("room", room);
	map.put("tel", tel);
	map.put("address", address);
	
	list.add(map);
}

for(int i2 = 0; i2 < list.size(); i2++) {
	String address = list.get(i2).get("address");
	// 카카오 위도/경도 API 호출    주소 => 위도/경도 변환
	NetworkUtil nu = new NetworkUtil();
	String url2 = "https://dapi.kakao.com/v2/local/search/address.json";
	String param = 
		"?query=" + URLEncoder.encode(address, "utf-8");
	String appKey = "d4be7b479f4b4cbd99bd19ae87f88b4b";
	String result = nu.getKakao(url2 + param, appKey);
	System.out.println(result);
	
	JSONObject json = new JSONObject(result);
	JSONArray documents = json.getJSONArray("documents");
	for(int i = 0; i < documents.length(); i++) {
		JSONObject doc = documents.getJSONObject(i);
		String lat = doc.getString("y");
		String lng = doc.getString("x");
		System.out.printf("%s, %s\n", lat, lng);
		
		StringBuffer query2 = new StringBuffer();
		query2.append("UPDATE HOSPITAL SET");
		query2.append("  LAT = ?, LNG = ? "); // sql 문장
		query2.append(" WHERE ADDRESS = ?"); // sql 문장

		PreparedStatement stmt2 = con.prepareStatement(query2.toString());
		stmt2.setString(1, lat);
		stmt2.setString(2, lng);
		stmt2.setString(3, address);

		stmt2.executeUpdate();

	}

}

rs.close();
stmt.close();
con.close();

ObjectMapper om = new ObjectMapper();
out.print( om.writeValueAsString(list) );
%>    








