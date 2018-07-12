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

if(request.getParameter("password")!=null){
	if(request.getParameter("nickname")!=null)
	{
		String password=request.getParameter("password");
		String userName= java.net.URLDecoder.decode(request.getParameter("nickname"),"UTF-8");//获取请求参数

		try {
			final String databaseURL = "jdbc:mysql://localhost/course?user=tomcat&password=tomcat&useUnicode=true&characterEncoding=UTF-8";
			Connection conn;
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(databaseURL);
      PreparedStatement check = conn.prepareStatement("SELECT * FROM BasicInfo WHERE nickname=?");
      check.setString(1, userName);
      ResultSet rs = check.executeQuery();
      if (rs.next()) success = 0;//如果找到了这样的nickname，表明重复，失败，false
      else {
          PreparedStatement stmt = conn.prepareStatement("SELECT MAX(userID) FROM BasicInfo");
          ResultSet tmp = stmt.executeQuery();
          tmp.next();
          int num = 1 + tmp.getInt("MAX(userID)");
          PreparedStatement operation = conn.prepareStatement("INSERT INTO BasicInfo (`userID`, `nickname`, `password`)  VALUES (?,?,?)");
          operation.setInt(1, num);
          operation.setString(2, userName);
          operation.setString(3, password);
          operation.execute();
          success=1;
      }
			conn.close();
    } catch (Exception e) {
				out.println(e);
        e.printStackTrace();
    }
	}
}
out.println(success);
%>
