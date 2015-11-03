package server;

import util.DatabaseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

/**
 * Created by ustc on 2015/10/27.
 */
@WebServlet(name = "GymBriefServlet")
public class GymBriefServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String type = request.getParameter("type");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("{");
        out.println("\"gym_brief\": [");

        String name = null;
        String longtitude = null;
        String latitude = null;
        String man_image = null;
        String single_price = null;
        String vip_price = null;
        String discount = null;
        ResultSet resultSet = null;

        try
        {
            if (type == null)//�����������Բ����ڣ�˵������������г���
            {
                resultSet = DatabaseUtil.getGymBriefAll();
            }
            else //type���Բ�Ϊnull��˵���������ĳһ�ೡ��
            {
                resultSet = DatabaseUtil.getGymBrief(type);
            }

            while (resultSet.next())
            {
                name = resultSet.getString(1);
                longtitude = String.valueOf(resultSet.getFloat(2));
                latitude = String.valueOf(resultSet.getFloat(3));
                man_image = resultSet.getString(4);
                single_price = String.valueOf(resultSet.getInt(5));
                vip_price = String.valueOf(resultSet.getInt(6));
                discount = String.valueOf(resultSet.getFloat(7));

                String everyGym = "{\"name\": \"" + name +
                        "\", \"longitude\": " + longtitude +
                        ", \"latitude\": " + latitude +
                        ", \"main_image\": \"" + man_image +
                        "\", \"single_price\": " + single_price +
                        ", \"vip_price\": " + vip_price +
                        ", \"discount\": " + discount + "},";

                everyGym = everyGym.substring(0, everyGym.length() -1);
                out.println(everyGym);
            }


        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        out.println("]");
        out.println("}");
    }
}
