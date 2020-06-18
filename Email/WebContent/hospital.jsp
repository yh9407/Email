<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
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

out.print("<table border='1'>");
while (rs.next()) {
	int id2 = rs.getInt("ID");
	String sido = rs.getString("SIDO");
	String name = rs.getString("NAME");
	String medical = rs.getString("MEDICAL");
	String room = rs.getString("ROOM");
	String tel = rs.getString("TEL");
	String address = rs.getString("ADDRESS");
	out.print("<tr>");
	out.print("<td>" + sido + "</td>");
	out.print("<td>" + name + "</td>");
	out.print("<td>" + medical + "</td>");
	out.print("<td>" + room + "</td>");
	out.print("<td>" + tel + "</td>");
	out.print("<td>" + address + "</td>");
	out.print("</tr>");
}
//http://localhost:8080/Email/hospital.jsp
out.print("</table>");
rs.close();
stmt.close();
con.close();
%>