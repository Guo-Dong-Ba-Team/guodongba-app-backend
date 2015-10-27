package util;

import javax.swing.*;
import java.sql.*;

/**
 * Created by ustc on 2015/10/26.
 */
public class DatabaseUtil
{
    private static Connection connection = null;

    public static Connection getConnect(String host, String database, String user, String password)
            throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, user, password);
//        return DriverManager.getConnection("jdbc:mysql://localhost/guodongba", "root", "");
    }

    public static void initConnection()
    {
        if (connection == null)
        {
            try
            {
                connection = getConnect("localhost", "guodongba", "root", "");

            } catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static int customerRegister(String registName, String password, String registerPhone)
            throws SQLException
    {
        final int FLAG_PHONE_HAS_REGISTERED = 1;
        final int FLAG_USERNAME_HAS_REGISTERED = 2;

        initConnection();
        Statement statement = connection.createStatement();

        //ÿ��ע��ʱ���ȼ����ֻ����Ƿ��Ѿ�ע��
        String s1 = "SELECT phone FROM customer_info WHERE phone = \'" + registerPhone + "\';";
        ResultSet resultSet = statement.executeQuery(s1);
        if (resultSet.next())
        {
            return FLAG_PHONE_HAS_REGISTERED;
        }

        //ÿ��ע��ʱ���ȼ���û����Ƿ��Ѿ�ע��
        String s2 = "SELECT username FROM customer_info WHERE username = \'" + registName + "\';";
        ResultSet resultSet2 = statement.executeQuery(s2);
        if (resultSet2.next())
        {
            return FLAG_USERNAME_HAS_REGISTERED;
        }

        String sentence = "INSERT INTO customer_info" + " VALUES ( \'" + registName + "\', \'" + password + "\',\'" + registerPhone + "\',1);";
        int result = statement.executeUpdate(sentence); //�û�ע��ʱ��Ĭ���ǵ�¼��

        statement.close();
        return 0;
    }

    public static int customerLogin(String phone, String password)
            throws SQLException
    {
        final int FLAG_PHONE_NOT_EXIST = 1;
        final int FLAG_PHONE_PASSWORD_WRONG = 2;

        initConnection();
        Statement statement = connection.createStatement();

        String sentence1 = "SELECT phone FROM customer_info WHERE phone = \'" + phone + "\';";
        ResultSet resultSet1 = statement.executeQuery(sentence1);
        if (!resultSet1.next())
        {
            return FLAG_PHONE_NOT_EXIST;
        }

        String sentence2 = "SELECT phone, password FROM customer_info WHERE phone = \'" + phone + "\' and password = \'" + password + "\';";
        ResultSet resultSet2 = statement.executeQuery(sentence2);
        if (!resultSet2.next())
        {
            return FLAG_PHONE_PASSWORD_WRONG;
        }

        return 0;
    }

    public static ResultSet getGymBriefAll()
            throws SQLException
    {
        initConnection();
        Statement statement = connection.createStatement();

        String sentence = "SELECT name, longtitude, latitude, main_image, single_price, vip_price, discount FROM gym_info;";
        ResultSet resultSet = statement.executeQuery(sentence);

        return resultSet;
    }

    public static ResultSet getGymBrief(String type)
            throws SQLException
    {
        initConnection();
        Statement statement = connection.createStatement();

        String sentence = "SELECT name, longtitude, latitude, main_image, single_price, vip_price, discount FROM gym_info WHERE type = " + type + ";";
        ResultSet resultSet = statement.executeQuery(sentence);

        return resultSet;
    }


    //详细信息是在点进去每个商家之后才请求的，所以详细信息总是只有一条记录
    public static ResultSet getGymDetail(String gym_id)
            throws SQLException
    {
        initConnection();
        Statement statement = connection.createStatement();

        String sentence = "SELECT name, longtitude, latitude, main_image, single_price, vip_price, discount, address_city, address_detail, phone, open_time, " +
                "hardware_info, service_info, star_level FROM gym_info WHERE id = " + gym_id + ";";
        ResultSet resultSet = statement.executeQuery(sentence);

        return resultSet;
    }

    //获取每个商家的详情图片
    public static ResultSet getGymImages(String gym_id)
            throws SQLException
    {
        initConnection();
        Statement statement = connection.createStatement();

        String sentence = "SELECT path FROM detail_images WHERE gym_id = " + gym_id + ";";
        ResultSet resultSet = statement.executeQuery(sentence);

        return resultSet;
    }

    public static float getGymStarLevel(String gym_id)
            throws SQLException
    {
        initConnection();
        Statement statement = connection.createStatement();

        String sentence = "SELECT AVG(star_level) FROM gym_star_level WHERE gym_id = " + gym_id + ";";
        ResultSet resultSet = statement.executeQuery(sentence);
        while (resultSet.next())
        {
            return resultSet.getFloat(1);
        }

        return 0; //如果返回0，表示当前商家还没有人评分
    }
}