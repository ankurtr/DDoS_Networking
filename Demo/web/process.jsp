<%-- 
    Document   : process
    Created on : Feb 11, 2012, 4:41:39 PM
    Author     : SiyaRam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        
        <%
        
        out.println(request.getParameter("txtUsername"));
        out.println(request.getParameter("txtPassword"));
        
        
        %>
        
        
    </body>
</html>
