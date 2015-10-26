package server;

import util.DatabaseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by ustc on 2015/10/26.
 */
@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        try
        {
            int result = DatabaseUtil.customerLogin(phone, password);

            response.setStatus(response.SC_OK);

            //返回值：0：登录成功；1：该手机号未注册；2.手机号或密码不正确
            PrintWriter out = response.getWriter();
            out.print(result);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
