package test;
import java.sql.*;
public class DatabaseImport {
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
	        @SuppressWarnings("unused")
			Connection dbConn=DriverManager.getConnection(dbURL,userName,userPwd);
	        System.out.println("连接成功！");
	      }catch(Exception e)
	      {
	        e.printStackTrace();
	        System.out.print("连接失败！");
	      }
	      Statement sql;
	      ResultSet rs;
			try {
				Connection dbConn=DriverManager.getConnection(dbURL,userName,userPwd);
				sql = dbConn.createStatement();
				rs = sql.executeQuery("SELECT* FROM participant");//SQL查询语句
				while (rs.next()) {
					String name = rs.getString(1);
					System.out.printf("%s\n", name);// name列
				}
				dbConn.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
	  }
}