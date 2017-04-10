<%-- 
    Document   : login
    Created on : Feb 11, 2012, 4:33:33 PM
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
        <form action="process.jsp" method="POST">
            
            username
            <input type="text" name="txtUsername"/>
            <br/>
            password
            <input type="password" name="txtPassword"/>
            <br/>
            <input type="submit" value="submit" name="btnSubmit"/>
            
        </form>
    </body>
</html>
