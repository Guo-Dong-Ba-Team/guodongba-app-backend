package server;

import util.DatabaseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.MonitorInfo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by ustc on 2015/10/27.
 */
@WebServlet(name = "OrderServlet")
public class OrderServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String user = request.getParameter("user");
        String status = request.getParameter("status");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("{");
        out.println("\"order_info\": [");

        String gym_id = "";
        String time = "";
        String money = "";

        try
        {
            if (user != null)
            {
                ResultSet resultSet = DatabaseUtil.getOrder(user, status);
                while (resultSet.next())
                {
                    gym_id = String.valueOf(resultSet.getInt(2));
                    time = resultSet.getDate(3).toString();
                    status = String.valueOf(resultSet.getInt(4));
                    money = String.valueOf(resultSet.getFloat(5));

                    String everyOrder = "{\"user\": \"" + user +
                            "\", \"gym_id\": " + gym_id +
                            ", \"time\": \"" + time +
                            "\", \"status\": " + status +
                            ", \"money\": " + money +"},";

                    everyOrder = everyOrder.substring(0, everyOrder.length() - 1);
                    out.println(everyOrder);
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        out.println("]");
        out.println("}");
    }
}
