package type03.util;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class DBManager {

	public static Connection getConnection(String dbName) {
		Connection conn = null;
		DataSource dataSource;
		try {
			switch (dbName) {
				case "mysql":
					dataSource = (DataSource) new InitialContext()
							.lookup("java:comp/env/jdbc/mySql");
					conn = dataSource.getConnection();
					break;
//				case "oracle":
//					dataSource = (DataSource) new InitialContext()
//							.lookup("java:comp/env/jdbc/myOracle");
//					conn = dataSource.getConnection();
//					break;
			}
		} catch (Exception e) {
			System.err.println("Exception: Database Connection Something Problem!!");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}
}
