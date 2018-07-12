<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.mysql.jdbc.Driver.*"%>
<%@page import="java.sql.*"%>
<%@page import="com.mysql.jdbc.ResultSetMetaData.*"%>
<%
int success=0;
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");

//下列是一系列注册时要写入的参数
int uid=(Integer)(session.getAttribute("userID"));
if(request.getParameter("postID")!=null){
  int pid=Integer.parseInt(request.getParameter("postID"));

	try {
		final String databaseURL = "jdbc:mysql://localhost/course?user=tomcat&password=tomcat&useUnicode=true&characterEncoding=UTF-8";
		Connection conn;
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(databaseURL);
    PreparedStatement rate = conn.prepareStatement("DELETE FROM Post WHERE postID=? AND userID=?");
    rate.setInt(1, pid);
    rate.setInt(2, uid);
    rate.execute();
    success = 1;
		conn.close();
  } catch (Exception e) {
		out.println(e);
    e.printStackTrace();
  }
}
out.println(success);
%>
