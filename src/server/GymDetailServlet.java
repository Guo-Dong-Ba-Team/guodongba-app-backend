package server;

import util.DatabaseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ustc on 2015/10/27.
 */
@WebServlet(name = "GymDetailServlet")
public class GymDetailServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String gym_id = request.getParameter("gym_id");
       // response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("{");
        out.println("\"gym_detail\": ");

        String name = null;
        String longtitude = null;
        String latitude = null;
        String man_image = null;
        String single_price = null;
        String vip_price = null;
        String discount = null;
        String addres_city = null;
        String addres_detail = null;
        String phone = null;
        String open_time = null;
        String hardware = null;
        String service = null;
        String star_level = null;


        ResultSet resultSet = null;

        try
        {
            if (gym_id != null)//�����ʽ��ȷ
            {
                resultSet = DatabaseUtil.getGymDetail(gym_id);
                while (resultSet.next())
                {
                    name = resultSet.getString(1);
                    longtitude = String.valueOf(resultSet.getFloat(2));
                    latitude = String.valueOf(resultSet.getFloat(3));
                    man_image = resultSet.getString(4);

                    //�����ݿ������󳡹�����ͼƬ
                    String detail_images = "";
                    ResultSet resultSet1 = DatabaseUtil.getGymImages(gym_id);
                    while (resultSet1.next())
                    {
                        detail_images += "\"" + resultSet1.getString(1) + "\",";
                    }
                    detail_images = detail_images.substring(0,detail_images.length() - 1);

                    single_price = String.valueOf(resultSet.getInt(5));
                    vip_price = String.valueOf(resultSet.getInt(6));
                    discount = String.valueOf(resultSet.getFloat(7));
                    addres_city = resultSet.getString(8);
                    addres_detail = resultSet.getString(9);
                    phone = resultSet.getString(10);
                    open_time = resultSet.getString(11);
                    hardware = resultSet.getString(12);
                    service = resultSet.getString(13);

                    //��ȡ�̼��Ǽ�
                    star_level = String.valueOf(DatabaseUtil.getGymStarLevel(gym_id));

                    String everyGym = "{\"name\": \"" + name +
                            "\", \"longitude\": " + longtitude +
                            ", \"latitude\": " + latitude +
                            ", \"main_image\": \"" + man_image +
                            "\", \"gym_image_url_array\":[ " + detail_images +
                            "], \"single_price\": " + single_price +
                            ", \"vip_price\": " + vip_price+
                            ", \"discount\": " + discount +
                            ", \"address_city\": \"" + addres_city +
                            "\", \"address_detail\": \"" + addres_detail +
                            "\", \"phone_num\": \"" + phone +
                            "\", \"open_time\": \"" + open_time +
                            "\", \"hardware\": \"" + hardware +
                            "\", \"service\": \"" + service +
                            "\", \"star_level\": " + star_level +
                            "}";

                    out.println(everyGym);
                }
            }
            out.flush();

        } catch (SQLException e)
        {
            e.printStackTrace();

        }

        out.println("}");
    }
}
