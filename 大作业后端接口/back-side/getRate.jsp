<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.mysql.jdbc.Driver.*"%>
<%@page import="java.sql.*"%>
<%@page import="com.mysql.jdbc.ResultSetMetaData.*"%>
<%
int success=0;
float score=0.0f;
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");

//下列是一系列注册时要写入的参数

if(request.getParameter("courseID")!=null){
  int cid=Integer.parseInt(request.getParameter("courseID"));
	try {
		final String databaseURL = "jdbc:mysql://localhost/course?user=tomcat&password=tomcat&useUnicode=true&characterEncoding=UTF-8";
		Connection conn;
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(databaseURL);
    PreparedStatement rate = conn.prepareStatement("SELECT AVG(score) from Rate WHERE courseID = ?");
    rate.setInt(1, cid);
    ResultSet rs = rate.executeQuery();
    rs.next();
    score = rs.getFloat("AVG(score)");
    success = 1;

		conn.close();
  } catch (Exception e) {
		out.println(e);
    e.printStackTrace();
  }
}
out.println(success);
out.println(score);
%>
