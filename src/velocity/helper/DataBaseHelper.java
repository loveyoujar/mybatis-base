package velocity.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 数据库帮助类集合.
 * <p>
 * 该数据库帮助类集合中提供了以下几个方法.<br>
 * 获取默认的数据库链接.<br>
 * 获取参数的数据库链接.<br>
 * 关闭数据库的链接.
 * 
 * @see Connection
 * @see DriverManager
 * @see ResultSet
 * @see Statement
 */
public class DataBaseHelper {

	/**
	 * 缺省的数据库链接地址.
	 */
	private static final String DEFAULT_URL = "jdbc:mysql://192.168.0.200/m_info?useUnicode=true&characterEncoding=UTF-8";

	/**
	 * 缺省的数据库链接用户名.
	 */
	private static final String DEFAULT_USE = "ms_info";

	/**
	 * 缺省的数据库链接密码.
	 */
	private static final String DEFAULT_PWS = "ms_info";

	/**
	 * 加载MySql数据库的链接驱动.
	 */
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取默认的数据库链接.
	 *
	 * @see Connection
	 * @see DataBaseHelper#DEFAULT_URL
	 * @see DataBaseHelper#DEFAULT_USE
	 * @see DataBaseHelper#DEFAULT_PWS
	 * @return java.sql.Connection
	 */
	public static Connection getConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(DEFAULT_URL, DEFAULT_USE, DEFAULT_PWS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 获取参数的数据库链接.
	 *
	 * @see Connection
	 * @see String
	 * @return java.sql.Connection
	 */
	public static Connection getConnection(String url, String username, String password) {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 关闭数据库的链接.
	 *
	 * @see ResultSet
	 * @see Statement
	 * @see Connection
	 */
	public static void close(ResultSet rs, Statement stm, Connection con) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (stm != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (con != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
