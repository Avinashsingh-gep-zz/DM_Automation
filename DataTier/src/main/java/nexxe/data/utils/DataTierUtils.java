package nexxe.data.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class DataTierUtils {

	public static String connectionUrl;
	public static Connection conn;

	public static Connection getDbConnection() {
		connectionUrl = "jdbc:sqlserver://gepqcdirect.database.windows.net:1433;" + "database=QC_SalesDemo_ShouldCost;"
				+ "user=RM_DBReader;" + "password=Password@123;" + "encrypt=true;" + "trustServerCertificate=false;"
				+ "loginTimeout=30;";

		try {
			conn = DriverManager.getConnection(connectionUrl);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public static Connection getDbConnection(String client, String env) throws IOException {

		HashMap<String, String> configInfo = DbJsonReader.readJsonFile(
				System.getProperty("user.dir") + DbPropertyReader.readConfig(DbConfigurationProperties.JSON_PATH), env,
				client);
		

		String userName = configInfo.get(DbKeys.UserName.toString());
		String passWord = configInfo.get(DbKeys.PassWord.toString());
		String dbConn = configInfo.get(DbKeys.DbConn.toString());
		String dataBase = configInfo.get(DbKeys.DataBase.toString());

		connectionUrl = "jdbc:sqlserver://" + dbConn + "" + "database=" + dataBase + "" + "user=" + userName + ""
				+ "password=" + passWord + "" + "encrypt=true;" + "trustServerCertificate=false;" + "loginTimeout=30;";
		try {
			conn = DriverManager.getConnection(connectionUrl);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

}
