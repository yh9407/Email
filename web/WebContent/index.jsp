<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>


<%
	// ���� �ۼ�
StringBuffer query = new StringBuffer();
query.append("SELECT ID, NAME, GENDER, KIND FROM ANIMAL"); //sql �� Table ���� , 

// 1�� ����̹� �ε�
Class.forName("com.mysql.jdbc.Driver");

// 2�� ����
String url = "jdbc:mysql://localhost:3306/java";
String id = "root";
String pw = "1234";
Connection con = DriverManager.getConnection(url, id, pw);

// 3�� �����غ�
PreparedStatement stmt = con.prepareStatement(query.toString());

// 4�� ����
ResultSet rs = stmt.executeQuery();


//out.println("<h2>" + "ANIMAL" +"</h2>");
out.println("<h2>" + "ANIMAL" +"</h2>"+ "<table border=1px solid #dddddd>");


// 5�� ��ȸ ��� ���� for each, iterate
while (rs.next()) {// �� �྿ ������ ��������.(next)
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