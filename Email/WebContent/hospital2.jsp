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
//?뒤에 아무것도 안적어도 나오게 

Class.forName("com.mysql.jdbc.Driver");//SQL문장

String url = "jdbc:mysql://localhost:3306/java";
String id = "root";
String pw = "1234";
Connection con = DriverManager.getConnection(url, id, pw);

StringBuffer query = new StringBuffer();
query.append("SELECT ID, SIDO, NAME, MEDICAL");
query.append("     , ROOM, TEL, ADDRESS");
query.append(" FROM HOSPITAL");
query.append(" WHERE ADDRESS LIKE CONCAT('%', ?, '%')");
//반드시 JSP뒤에 ? 뒤에 뭔가를 넣어야함.

PreparedStatement stmt = con.prepareStatement(query.toString());

stmt.setString(1, area);
ResultSet rs = stmt.executeQuery();

List<Map<String, String>>list = new ArrayList<>();

while (rs.next()) {
	
	Map<String, String> map = new HashMap<>();
	//map 정의
	int id2 = rs.getInt("ID");
	String sido = rs.getString("SIDO");
	String name = rs.getString("NAME");
	String medical = rs.getString("MEDICAL");
	String room = rs.getString("ROOM");
	String tel = rs.getString("TEL");
	String address = rs.getString("ADDRESS");
	
	map.put("id", id2 + "") ;
	map.put("sido", sido);
	map.put("name", name);
	map.put("medical", medical);
	map.put("room", room);
	map.put("tel", tel);
	map.put("address", address);
	//맵에 변수들 넣음
	
	list.add(map);
	//list에 변수들이 담긴 맵을 넣음.
}
rs.close();
stmt.close();
con.close();

ObjectMapper om = new ObjectMapper();
//Jacson 쓸려면 필요한 코드 '중요'
out.print( om.writeValueAsString(list) );
//담겨진 데이터를 모두 json으로 변환시키는 코드.
%>



