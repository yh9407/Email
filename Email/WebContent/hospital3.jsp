<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="email.NetworkUtil"%>
<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="application/json; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<%
	String area = request.getParameter("area");
if (area == null)
	area = "";
//?�ڿ� �ƹ��͵� ����� ������ 

Class.forName("com.mysql.jdbc.Driver");//SQL����

String url = "jdbc:mysql://localhost:3306/java";
String id = "root";
String pw = "1234";
Connection con = DriverManager.getConnection(url, id, pw);

StringBuffer query = new StringBuffer();
query.append("SELECT ADDRESS");
query.append(" FROM HOSPITAL");
query.append(" WHERE ADDRESS LIKE CONCAT('%', ?, '%')");
//�ݵ�� JSP�ڿ� ? �ڿ� ������ �־����.

PreparedStatement stmt = con.prepareStatement(query.toString());

stmt.setString(1, area);
ResultSet rs = stmt.executeQuery();

List<Map<String, String>>list = new ArrayList<>();

while (rs.next()) {
	
	Map<String, String> map = new HashMap<>();
	//map ����
	String address = rs.getString("ADDRESS");
	
	map.put("address", address);
	//�ʿ� ������ ����
	
	list.add(map);
	//list�� �������� ��� ���� ����.
}

rs.close();
stmt.close();
con.close();

ObjectMapper om = new ObjectMapper();
//Jacson ������ �ʿ��� �ڵ� '�߿�'
out.print( om.writeValueAsString(list));
//����� �����͸� ��� json���� ��ȯ��Ű�� �ڵ�.

NetworkUtil nu = new NetworkUtil();
String url2 = "https://dapi.kakao.com/v2/local/search/address.json";
String param = "?query=" + URLEncoder.encode("�λ� ������ ���굿 1000", "utf-8");
String appKey = "d4be7b479f4b4cbd99bd19ae87f88b4b";
String result = nu.getKakao(url + param, appKey);
System.out.println(result);

JSONObject json = new JSONObject (result);
JSONArray documents = json.getJSONArray("documents");
for(int i = 0; i < documents.length(); i++) {
	JSONObject doc = documents.getJSONObject(i);
	String lat = doc.getString("y");
	String lng = doc.getString("x");
	System.out.printf("%s, %s\n", lat, lng);
}


%>




