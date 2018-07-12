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
if(request.getParameter("courseID")!=null && request.getParameter("score")!=null){

  int cid=Integer.parseInt(request.getParameter("courseID"));
  float score=Float.parseFloat(request.getParameter("score"));

	try {
		final String databaseURL = "jdbc:mysql://localhost/course?user=tomcat&password=tomcat&useUnicode=true&characterEncoding=UTF-8";
		Connection conn;
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(databaseURL);
    PreparedStatement rate = conn.prepareStatement("REPLACE INTO Rate(`userID`,`courseID`,`score`) VALUES(?,?,?)");
    rate.setInt(1, uid);
    rate.setInt(2, cid);
    rate.setFloat(3, score);
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
