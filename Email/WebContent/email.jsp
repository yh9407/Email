<%@page import="email.Mailer"%>
<%@page import="email.SMTPAuthenticator"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%
response.setHeader("Access-Control-Allow-Origin","*");
//�񵿱� ��� (ajax) ���� �°��� �ϴ� ���ܵ�, ������� ���������� ��. 
//
String name = request.getParameter("name");
String email = request.getParameter("email");
String phone = request.getParameter("phone");
String message = request.getParameter("message");

//System.out.println(name);
//System.out.println(email);
//System.out.println(phone);
//System.out.println(message);

String to = email;
String subject = name;
String content = "<h1>"+ message + phone +  "</h1>";
SMTPAuthenticator smtp = new SMTPAuthenticator();
Mailer mailer = new Mailer();
mailer.sendMail(to, subject, content, smtp);

%>

