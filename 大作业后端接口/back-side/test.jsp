<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%

	//连接数据库，以某种方式查询结果为rs
  try {
    final String databaseURL = "jdbc:mysql://localhost/course?user=tomcat&password=tomcat&useUnicode=true&characterEncoding=UTF-8";
    Connection conn;
    Class.forName("org.gjt.mm.mysql.Driver").newInstance();
    conn = DriverManager.getConnection(databaseURL);
    PreparedStatement check = conn.prepareStatement("SELECT * FROM BasicInfo");
    ResultSet rs = check.executeQuery();

	while(rs.next()){
	  out.println(rs.getString("nickname"));
	}
    conn.close();
  } catch (Exception e) {
    out.println(e);
    e.printStackTrace();
  }
	//连接数据库，以某种方式查询结果为rs
%>
