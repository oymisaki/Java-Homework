<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>

<%
	if(session.getAttribute("login")!=null && session.getAttribute("login").equals("ok")){
		session.setAttribute("login",null);
		session.setAttribute("userID",null);
		session.setAttribute("thisPageID",null);
	}
  out.println(1);
%>
</body>
