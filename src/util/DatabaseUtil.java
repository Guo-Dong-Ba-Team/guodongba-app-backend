package util;

import java.sql.*;

/**
 * Created by ustc on 2015/10/26.
 */
public class DatabaseUtil
{
    private  static Connection connection = null;

    public  static Connection getConnect(String host, String database, String user, String password)
            throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
//        return DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, user, password);
        return DriverManager.getConnection("jdbc:mysql://localhost/guodongba", "root", "");
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

        //每次注册时，先检查该手机号是否已经注册
        String s1 = "SELECT phone FROM customer_info WHERE phone = \'" + registerPhone+"\';";
        ResultSet resultSet = statement.executeQuery(s1);
        if (resultSet.next())
        {
            return FLAG_PHONE_HAS_REGISTERED;
        }

        //每次注册时，先检查用户名是否已经注册
        String s2 = "SELECT username FROM customer_info WHERE username = \'" + registName + "\';";
        ResultSet resultSet2 = statement.executeQuery(s2);
        if (resultSet2.next())
        {
            return FLAG_USERNAME_HAS_REGISTERED;
        }

        String sentence = "INSERT INTO customer_info" + " VALUES ( \'" + registName + "\', \'" + password + "\',\'" + registerPhone + "\',1);";
        int result = statement.executeUpdate(sentence); //用户注册时，默认是登录的

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
}
