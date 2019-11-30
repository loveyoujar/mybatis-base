package velocity.utils;

import JdbcDemo.JdbcDemo;
import velocity.entity.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 实体类函数集合.
 * <p>
 * 该实体类函数集合提供以下几个方法.<br>
 * 转换实体属性名驼峰写法.<br>
 * 转换实体属性方法名驼峰写法.<br>
 * 根据实体属性构建对应的DTO集合.<br>
 * 
 * @see Connection
 * @see PreparedStatement
 * @see ResultSet
 * @see ArrayList
 * @see List
 */
public class EntityUtils {

	/**
	 * 转换实体属性名驼峰写法.<br>
	 * 如果数据库字段名称包含下划线.<br>
	 * 则把转换成驼峰的写法.
	 * 
	 * @param attrName
	 *            数据库字段名
	 * @return java.lang.String
	 */
	private static String getAttrName(String attrName) {
		if (attrName.contains("_")) {
			String attrNameStr[] = attrName.split("_");
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < attrNameStr.length; i++) {
				if (i == 0) {
					sb.append(attrNameStr[i].toLowerCase());
				} else {
					String str = attrNameStr[i].toLowerCase();
					if (str.length() > 1) {
						String beginStr = str.substring(0, 1);
						sb.append(beginStr.toUpperCase()).append(str.substring(1));
					} else {
						sb.append(str);
					}
				}
			}
			attrName = sb.toString();
		} else {
			attrName = attrName.toLowerCase();
		}
		return attrName;
	}

	/**
	 * 转换实体属性方法名驼峰写法.<br>
	 * 如果实体属性方法名称包含下划线.<br>
	 * 则把转换成驼峰的写法.
	 * 
	 * @param methodName
	 *            方法名称
	 * @return java.lang.String
	 */
	private static String getMehodName(String methodName) {
		boolean flag = false;
		if (methodName.contains("_")) {
			String attrNameStr[] = methodName.split("_");
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < attrNameStr.length; i++) {
				if (i == 0) {
					if (attrNameStr[i].length() > 1) {
						flag = true;
					}
					sb.append(attrNameStr[i].toLowerCase());
				} else {
					String str = attrNameStr[i].toLowerCase();
					if (str.length() > 1) {
						String beginStr = str.substring(0, 1);
						sb.append(beginStr.toUpperCase()).append(str.substring(1));
					} else {
						sb.append(str);
					}
				}
			}
			methodName = sb.toString();
		} else {
			methodName = methodName.toLowerCase();
			methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
		}
		if (flag) {
			methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
		}
		return methodName;
	}

	/**
	 * 根据实体属性构建对应的DTO集合.
	 * 
	 * @param connection
	 *            数据库链接
	 * @param database
	 *            数据库名
	 * @param tablename
	 *            表名称
	 * @return List 实体属性类Entity的集合
	 */
	@SuppressWarnings({ "unchecked", "unused", "static-access", "rawtypes" })
	public static List<Entity> getCloumnToDTO(Connection connection, String database, String tablename) {
		List list = new ArrayList<Entity>();
		JdbcDemo jdbcdemo = new JdbcDemo();
		Connection con = connection;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = new StringBuffer().append("select COLUMN_NAME,DATA_TYPE,COLUMN_COMMENT,COLUMN_DEFAULT from INFORMATION_SCHEMA.Columns where table_name='").append(tablename).append("' and table_schema='").append(database).append("'").toString();
		try {
			ps = con.prepareStatement(sql);
			if (ps.execute()) {
				rs = ps.getResultSet();
			} else {
				int i = ps.getUpdateCount();
			}
			while (rs.next()) {
				StringBuffer str = new StringBuffer();
				String data_type = rs.getString("DATA_TYPE");
				String type = "";
				if (data_type.equals("int") || data_type.equals("bigint") || data_type.equals("tinyint")) {
					type = "Integer";
				} else if (data_type.equals("varchar")) {
					type = "String";
				} else if (data_type.equals("decimal")) {
					type = "Double";
				} else if (data_type.equals("text")) {
					type = "String";
				} else if (data_type.equals("datetime")) {
					type = "Date";
				}
				Entity entity = new Entity();
				entity.setJdbcType(data_type);
				entity.setJavaType(type);
				entity.setColumn(rs.getString("COLUMN_NAME"));
				entity.setProperty(getAttrName(rs.getString("COLUMN_NAME")));
				entity.setMethodName(getMehodName(rs.getString("COLUMN_NAME")));
				entity.setColumnComment(rs.getString("COLUMN_COMMENT"));
				entity.setColumnDefault(rs.getString("COLUMN_DEFAULT"));
				list.add(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcdemo.close(rs, ps, con);
		}
		return list;
	}
}
