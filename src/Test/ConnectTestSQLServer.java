package test;
import java.sql.*;
public class ConnectTestSQLServer {
	  @SuppressWarnings("unused")
	public static void main(String [] args)
	  {
	      String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	      String dbURL="jdbc:sqlserver://0.0.0.0:1443;DatabaseName=test";//这里的test是数据库名称
	      String userName="sa";//这里的sa是数据库用户名，默认是sa
	      String userPwd="miku0139";//这里是密码
	      try
	      {
	        Class.forName(driverName);
	        System.out.println("加载驱动成功！");
	      }catch(Exception e)
	      {
	        e.printStackTrace();
	        System.out.println("加载驱动失败！");
	      }
	      try{
	        Connection dbConn=DriverManager.getConnection(dbURL,userName,userPwd);
	        System.out.println("连接成功！");
	      }catch(Exception e)
	      {
	        e.printStackTrace();
	        System.out.print("连接失败！");
	      }
	      
	  }
}