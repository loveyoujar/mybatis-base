package JdbcDemo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 创建方法实体处理类
 * @author neo
 * @since 2013-01-01
 */
public class Create {

	@SuppressWarnings( { "static-access", "unused" })
	public void UpdateProductFCKHtml() {
		JdbcDemo jdbcdemo = new JdbcDemo();
		Connection con = jdbcdemo.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = new StringBuffer().append("select * from t_product").toString();
		try {
			ps = con.prepareStatement(sql);
			if (ps.execute()) {
				rs = ps.getResultSet();
			} else {
				int i = ps.getUpdateCount();
			}
			while (rs.next()) {
				// String short_desc=rs.getString("SHORT_DESC");
				if (rs.getString("ID").equals("280")) {
					System.out.println("This project is error ...");
				}
				if (rs.getString("DETAIL_DESC") != "" && rs.getString("DETAIL_DESC") != null) {
					String detail_desc = StringUtil.covertUnicode(rs.getString("DETAIL_DESC"));
					String update_sql = new StringBuffer().append("update t_product set DETAIL_DESC=?").append(" where id=").append(rs.getString("ID")).toString();
					ps = con.prepareStatement(update_sql);
					ps.setString(1, detail_desc);
					ps.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcdemo.close(rs, ps, con);
		}
	}

	@SuppressWarnings( { "static-access", "unused" })
	public void CreatePojo(String database, String tablename) {
		JdbcDemo jdbcdemo = new JdbcDemo();
		Connection con = jdbcdemo.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = new StringBuffer().append("select COLUMN_NAME,DATA_TYPE from INFORMATION_SCHEMA.Columns where table_name='").append(tablename).append("' and table_schema='").append(database).append("'").toString();
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

				if (data_type.equals("int")) {
					type = "Integer";
				} else if (data_type.equals("varchar")) {
					type = "String";
				} else if (data_type.equals("decimal")) {
					type = "Double";
				} else if (data_type.equals("text")) {
					type = "String";
				} else if (data_type.equals("datetime")) {
					type = "Date";
				}else if (data_type.equals("char")) {
					type = "String";
				}
				str.append("private ").append(type).append("  ").append(rs.getString("COLUMN_NAME").toLowerCase()).append(";");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcdemo.close(rs, ps, con);
		}
	}

	@SuppressWarnings( { "static-access", "unused" })
	public void CreatePojoXml(String database, String tablename) {
		JdbcDemo jdbcdemo = new JdbcDemo();
		Connection con = jdbcdemo.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = new StringBuffer().append("select COLUMN_NAME,DATA_TYPE from INFORMATION_SCHEMA.Columns where table_name='").append(tablename).append("' and table_schema='").append(database).append("'").toString();
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

				if (data_type.equals("int")) {
					type = "int";
				} else if (data_type.equals("varchar")) {
					type = "String";
				} else if (data_type.equals("decimal")) {
					type = "double";
				} else if (data_type.equals("text")) {
					type = "String";
				} else if (data_type.equals("datetime")) {
					type = "Date";
				}
				str.append("<result jdbcType=\"").append(type).append("\" property=\"").append(rs.getString("COLUMN_NAME").toLowerCase()).append("\" column=\"").append(rs.getString("COLUMN_NAME").toUpperCase()).append("\"></result>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcdemo.close(rs, ps, con);
		}
	}

	@SuppressWarnings( { "static-access", "unused" })
	public void CreateInsert(String database, String tablename) {
		JdbcDemo jdbcdemo = new JdbcDemo();
		Connection con = jdbcdemo.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = new StringBuffer().append("select COLUMN_NAME,DATA_TYPE from INFORMATION_SCHEMA.Columns where table_name='").append(tablename).append("' and table_schema='").append(database).append("'").toString();
		try {
			ps = con.prepareStatement(sql);
			if (ps.execute()) {
				rs = ps.getResultSet();
			} else {
				int i = ps.getUpdateCount();
			}

			StringBuffer str = new StringBuffer();
			str.append("insert into ").append(tablename).append(" (");

			StringBuffer items = new StringBuffer();
			StringBuffer values = new StringBuffer();
			while (rs.next()) {
				String data_type = rs.getString("DATA_TYPE");
				String type = "";

				if (data_type.equals("int")) {
					type = "int";
				} else if (data_type.equals("varchar")) {
					type = "String";
				} else if (data_type.equals("decimal")) {
					type = "double";
				} else if (data_type.equals("text")) {
					type = "String";
				} else if (data_type.equals("datetime")) {
					type = "Date";
				}

				items.append(rs.getString("COLUMN_NAME").toUpperCase()).append(",");
				values.append("#").append(rs.getString("COLUMN_NAME").toLowerCase()).append("#").append(",");

			}
			str.append(items);
			str.append(")VALUES (");
			str.append(values);
			str.append(")");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcdemo.close(rs, ps, con);
		}
	}

	@SuppressWarnings( { "static-access", "unused" })
	public void CreateUpdate(String database, String tablename) {
		JdbcDemo jdbcdemo = new JdbcDemo();

		Connection con = jdbcdemo.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = new StringBuffer().append("select COLUMN_NAME,DATA_TYPE from INFORMATION_SCHEMA.Columns where table_name='").append(tablename).append("' and table_schema='").append(database).append("'").toString();
		try {
			ps = con.prepareStatement(sql);
			if (ps.execute()) {
				rs = ps.getResultSet();
			} else {
				int i = ps.getUpdateCount();
			}

			StringBuffer str = new StringBuffer();
			str.append("update ").append(tablename).append(" set ");

			while (rs.next()) {
				StringBuffer items1 = new StringBuffer();
				StringBuffer items2 = new StringBuffer();
				String data_type = rs.getString("DATA_TYPE");
				String type = "";
				if (data_type.equals("int")) {
					type = "int";
				} else if (data_type.equals("varchar")) {
					type = "String";
				} else if (data_type.equals("decimal")) {
					type = "double";
				} else if (data_type.equals("text")) {
					type = "String";
				} else if (data_type.equals("datetime")) {
					type = "Date";
				}
				items1.append("<isNotNull property=\"").append(rs.getString("COLUMN_NAME").toLowerCase()).append("\" prepend=\",\">");
				items2.append(rs.getString("COLUMN_NAME").toUpperCase()).append("=#").append(rs.getString("COLUMN_NAME").toLowerCase()).append("#");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcdemo.close(rs, ps, con);
		}
	}

	@SuppressWarnings( { "static-access", "unused" })
	public void CreateSelect(String database, String tablename) {
		JdbcDemo jdbcdemo = new JdbcDemo();
		Connection con = jdbcdemo.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = new StringBuffer().append("select COLUMN_NAME,DATA_TYPE from INFORMATION_SCHEMA.Columns where table_name='").append(tablename).append("' and table_schema='").append(database).append("'").toString();
		try {
			ps = con.prepareStatement(sql);
			if (ps.execute()) {
				rs = ps.getResultSet();
			} else {
				int i = ps.getUpdateCount();
			}

			while (rs.next()) {
				StringBuffer str = new StringBuffer();
				str.append(rs.getString("COLUMN_NAME").toLowerCase()).append(",");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcdemo.close(rs, ps, con);
		}
	}
}
