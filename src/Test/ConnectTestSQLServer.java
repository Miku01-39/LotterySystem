package test;
import java.sql.*;
public class ConnectTestSQLServer {
	  @SuppressWarnings("unused")
	public static void main(String [] args)
	  {
	      String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	      String dbURL="jdbc:sqlserver://0.0.0.0:1443;DatabaseName=test";//�����test�����ݿ�����
	      String userName="sa";//�����sa�����ݿ��û�����Ĭ����sa
	      String userPwd="miku0139";//����������
	      try
	      {
	        Class.forName(driverName);
	        System.out.println("���������ɹ���");
	      }catch(Exception e)
	      {
	        e.printStackTrace();
	        System.out.println("��������ʧ�ܣ�");
	      }
	      try{
	        Connection dbConn=DriverManager.getConnection(dbURL,userName,userPwd);
	        System.out.println("���ӳɹ���");
	      }catch(Exception e)
	      {
	        e.printStackTrace();
	        System.out.print("����ʧ�ܣ�");
	      }
	      
	  }
}