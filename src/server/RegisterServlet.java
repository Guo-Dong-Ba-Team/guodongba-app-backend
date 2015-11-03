package server;

import util.DatabaseUtil;

import javax.rmi.CORBA.Util;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.sql.SQLException;

/**
 * Created by ustc on 2015/10/26.
 */
@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        try
        {
            int result = DatabaseUtil.customerRegister(userName, password, phone);

            response.setStatus(response.SC_OK);
            response.setCharacterEncoding("UTF-8");
            //3�ֽ����0��ע��ɹ���1.����ֻ����Ѿ���ע�᣻2.����ǳ��Ѿ���ע�᣻
            PrintWriter out = response.getWriter();
            out.print(result);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
