package com.server.tools;
import java.sql.Connection;
import java.sql.DriverManager;

class Test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String driverName = "com.mysql.cj.jdbc.Driver";
        String dbURL = "jdbc:sqlserver://127.0.0.1\\JSS;DatabaseName=chat";

        String userName = "root";

        String userPwd = "123456";

        Connection dbConn = null;

        try {

            Class.forName(driverName);

            dbConn = DriverManager.getConnection(dbURL, userName, userPwd);

            System.out.println("连接数据库成功");

        } catch (Exception e) {

            e.printStackTrace();

            System.out.print("连接失败");

        }
    }
}