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

if(request.getParameter("name")!=null || request.getParameter("id")!=null || request.getParameter("department")!=null || request.getParameter("prof")!=null){
  try{
    final String databaseURL = "jdbc:mysql://localhost/course?user=tomcat&password=tomcat&useUnicode=true&characterEncoding=UTF-8";
    Connection conn;
    Class.forName("com.mysql.jdbc.Driver");
    conn = DriverManager.getConnection(databaseURL);
    PreparedStatement stmt = null;
    if(request.getParameter("id") != null){
      Integer courseID = Integer.parseInt(request.getParameter("id"));
      stmt = conn.prepareStatement("SELECT * FROM Course WHERE courseID=?");
      stmt.setInt(1, courseID);
    }
    else if(request.getParameter("name")!=null){
    	String courseName= java.net.URLDecoder.decode(request.getParameter("name"),"UTF-8");//获取请求参数
      courseName = "%" + courseName +"%";
      stmt = conn.prepareStatement("SELECT * FROM Course WHERE name like ?");
      stmt.setString(1, courseName);
    }
    else if(request.getParameter("department")!=null){
    	String department= java.net.URLDecoder.decode(request.getParameter("department"),"UTF-8");//获取请求参数
      department = "%" + department + "%";
      stmt = conn.prepareStatement("SELECT * FROM Course WHERE department like ?");
      stmt.setString(1, department);
    }
    else{
    	String prof= java.net.URLDecoder.decode(request.getParameter("prof"),"UTF-8");//获取请求参数
      prof = "%" + prof + "%";
      stmt = conn.prepareStatement("SELECT * FROM Course WHERE prof like ?");
      stmt.setString(1, prof);
    }
    ResultSet rs = stmt.executeQuery();

    JSONArray courselist=new JSONArray();
    int num = 0;
    while(rs.next()){
      JSONObject info=new JSONObject();
      info.put("name", rs.getString("name"));
      info.put("prof", rs.getString("prof"));
      info.put("department", rs.getString("department"));
      info.put("intro", rs.getString("intro"));

      int cid = rs.getInt("courseID");
      info.put("courseID", cid);
      // 获取评分
      PreparedStatement rate = conn.prepareStatement("SELECT AVG(score) from Rate WHERE courseID = ?");
      rate.setInt(1, cid);
      ResultSet rs1 = rate.executeQuery();
      rs1.next();
      float score = rs1.getFloat("AVG(score)");
      info.put("score", score);
      courselist.put(info);
      num++;
    }

    JSONObject result = new JSONObject();
    result.put("number", num);
    result.put("course", courselist);
    conn.close();

    out.print(result.toString());
  }catch (Exception e) {
    e.printStackTrace();
  }
}
%>
