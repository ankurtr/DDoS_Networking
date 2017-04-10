/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;


/**
 *
 * @author SiyaRam
 */
public class DemoServlet extends HttpServlet {

   /* @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { 
        
        PrintWriter pw=resp.getWriter();
        
        Enumeration<String> params=req.getHeaderNames();
        while(params.hasMoreElements())
        {
            String elem=params.nextElement();
            System.out.println(elem + " : " + req.getHeader(elem));
        }
        
        
        //JOptionPane.showMessageDialog(null, "get");
        
       
        Enumeration<String> req_params=req.getParameterNames();
        while(req_params.hasMoreElements())
        {
            String pm=req_params.nextElement();
            System.out.println(pm + " : " + req.getParameter(pm));
        }
        
        //StringBuilder sb=new StringBuilder();
        /*sb.append("<html><body>");
        sb.append("<form method=GET action=Rajan.aspx>");
        sb.append("Enter Name<input type=text name=txtUsername>");
        sb.append("<br>Enter Password<input type=password name=txtPassword>");
        sb.append("<br><input type=submit name=btnSubmit value=submit >");
        sb.append("<input type=reset name=btnReset value=reset >");*/
        
        
       // JOptionPane.showMessageDialog(null, req.getParameter("txtUsername"));        
        //JOptionPane.showMessageDialog(null, req.getParameter("txtPassword"));  
        
        
        //pw.println(sb.toString());        
        
        
   // }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        JOptionPane.showMessageDialog(null, "post");        
        
        Enumeration<String> req_params=req.getParameterNames();
        while(req_params.hasMoreElements())
        {
            String pm=req_params.nextElement();
            System.out.println(pm + " : " + req.getParameter(pm));
        }
                
        
        JOptionPane.showMessageDialog(null, req.getParameter("txtUsername"));        
        JOptionPane.showMessageDialog(null, req.getParameter("txtPassword"));        
    }

  
}
