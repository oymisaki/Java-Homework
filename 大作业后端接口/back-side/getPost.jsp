<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.mysql.jdbc.Driver.*"%>
<%@page import="java.sql.*"%>
<%@page import="com.mysql.jdbc.ResultSetMetaData.*"%>
<%@page import="net.sf.json.JSON" %>
<%@page import="net.sf.json.JSONObject" %>
<%@page import="net.sf.json.JSONArray" %>
<%@page import="net.sf.json.xml.XMLSerializer" %>

<%
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");

if(request.getParameter("courseID")!=null){
  try{
    int cid = Integer.parseInt(request.getParameter("courseID"));

    final String databaseURL = "jdbc:mysql://localhost/course?user=tomcat&password=tomcat&useUnicode=true&characterEncoding=UTF-8";
    Connection conn;
    Class.forName("com.mysql.jdbc.Driver");
    conn = DriverManager.getConnection(databaseURL);

    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Post WHERE courseID=?");
    stmt.setInt(1, cid);

    ResultSet rs = stmt.executeQuery();

    JSONArray postlist=new JSONArray();
    int num = 0;

    while(rs.next()){
      JSONObject info=new JSONObject();
      info.put("postID", rs.getInt("postID"));
      info.put("userID", rs.getInt("userID"));
      info.put("content", rs.getString("content"));
      postlist.put(info);
      num++;
    }

    JSONObject result = new JSONObject();
    result.put("number", num);
    result.put("post", postlist);
    conn.close();

    out.print(result.toString());
  }catch (Exception e) {
    out.println(e);
    e.printStackTrace();
  }
}
%>
