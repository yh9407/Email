<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>


<%
	// 쿼리 작성
StringBuffer query = new StringBuffer();
query.append("SELECT ID, NAME, GENDER, KIND FROM ANIMAL"); //sql 의 Table 내용 , 

// 1번 드라이버 로딩
Class.forName("com.mysql.jdbc.Driver");

// 2번 연결
String url = "jdbc:mysql://localhost:3306/java";
String id = "root";
String pw = "1234";
Connection con = DriverManager.getConnection(url, id, pw);

// 3번 실행준비
PreparedStatement stmt = con.prepareStatement(query.toString());

// 4번 실행
ResultSet rs = stmt.executeQuery();


//out.println("<h2>" + "ANIMAL" +"</h2>");
out.println("<h2>" + "ANIMAL" +"</h2>"+ "<table border=1px solid #dddddd>");


// 5번 조회 결과 추출 for each, iterate
while (rs.next()) {// 한 행씩 데이터 가져오기.(next)
	int id2 = rs.getInt("ID");
	String name = rs.getString("NAME");
	String gender = rs.getString("GENDER");
	String kind = rs.getString("KIND");
	
	out.println("<tr>");
	out.println("<td>"+ id2 + "</td>");
	out.println("<td>"+ name + "</td>");
	out.println("<td>"+ gender+ "</td>");
	out.println("<td>"+ kind + "</td>");
	out.println("</tr>");
	
	
}
out.println("</table>");
rs.close();
stmt.close();
con.close();
%>