<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.mysql.jdbc.Driver.*"%>
<%@page import="java.sql.*"%>
<%@page import="com.mysql.jdbc.ResultSetMetaData.*"%>
<%
int success=0;
response.setCharacterEncoding("UTF-8");
request.setCharacterEncoding("UTF-8");

if(request.getParameter("nickname")!=null && request.getParameter("password")!=null){
	String nickname=request.getParameter("nickname");
	String password=request.getParameter("password");

	try {
		final String databaseURL = "jdbc:mysql://localhost/course?user=tomcat&password=tomcat&useUnicode=true&characterEncoding=UTF-8";
		Connection conn;
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(databaseURL);
		PreparedStatement check = conn.prepareStatement("SELECT * FROM BasicInfo WHERE nickname=?");
		check.setString(1, nickname);
		ResultSet rs = check.executeQuery();
		if (rs.next() && rs.getString("password").equals(password)){
			success=1;
			int userID=rs.getInt("userID");
		  conn.close();
			session.setAttribute("login","ok");
			session.setAttribute("userID",userID);
			session.setAttribute("thisPageID",userID);
			session.setMaxInactiveInterval(-1);
		}
	}catch (Exception e) {
	  e.printStackTrace();
	}
}
out.println(success);
%>
