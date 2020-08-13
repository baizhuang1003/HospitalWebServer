package com.tianyuan.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EntityFreamwork {
	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;
	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	private static final String DBFlag_Mariadb = "MARIADB";
	private static final String DBFlag_Oracle = "ORACLE";
	private static final String DBFlag_MySql = "MYSQL";
	private static final String DBFlag_SQLServer = "SQLSERVER";

	private boolean isMariadb() {
		if (driverClassName == null || driverClassName.equals(""))
			return false;
		return driverClassName.toUpperCase().indexOf(DBFlag_Mariadb) > -1;
	}

	private boolean isOracle() {
		if (driverClassName == null || driverClassName.equals(""))
			return false;
		return driverClassName.toUpperCase().indexOf(DBFlag_Oracle) > -1;
	}

	@SuppressWarnings("unused")
	private boolean isMySql() {
		if (driverClassName == null || driverClassName.equals(""))
			return false;
		return driverClassName.toUpperCase().indexOf(DBFlag_MySql) > -1;
	}

	@SuppressWarnings("unused")
	private boolean isSQLServer() {
		if (driverClassName == null || driverClassName.equals(""))
			return false;
		return driverClassName.toUpperCase().indexOf(DBFlag_SQLServer) > -1;
	}

	public EntityFreamwork() {
		super();
	}

	public EntityFreamwork(String driverClassName, String url, String username, String password) {
		this.driverClassName = driverClassName;
		this.username = username;
		this.url = url;
		this.password = password;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private Connection conn = null;

	public synchronized  Connection getConnection() throws SQLException {
		if (conn == null || conn.isClosed()) {
			conn = null;
			try {
				DriverManager.setLoginTimeout(3);
				Class.forName(driverClassName);
				conn = DriverManager.getConnection(url, username, password);
			} catch (SQLException | ClassNotFoundException e) {
				System.out.println("error:" + e.getMessage());
				return getConnection();
			}
		}
		return conn;
	}
	
	public void connectionTest() {
		Connection conn;
		Statement smt;
		ResultSet rs;
		try {
			conn = getConnection();
			smt = conn.createStatement();
			rs = smt.executeQuery("select 1 from dual");
			//rs.getInt(1);
			rs.close();
			smt.close();
		}
		catch(SQLException ex) {
			System.out.println("error:" + ex.getMessage());
		}
	}


	
	public String getId() {
		return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}

	public int intFormat(boolean b) {
		if (b)
			return 1;
		else
			return 0;
	}

	public String shortTimeFormat(Date date) {
		if (date == null)
			return "1900-01-01";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	public String longTimeFormart(Date date) {
		if (date == null)
			return "1900-01-01 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	@SuppressWarnings("unchecked")
	public <T> T insert(String sql) {
		T t = null;
		if (sql == null || sql.equals(""))
			return null;
		Connection conn;
		PreparedStatement psmt;
		ResultSet rs;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			psmt.executeUpdate();
			rs = psmt.getGeneratedKeys();
			if (rs.next()) {
				t = (T) rs.getObject(1);
			}
			rs.close();
			psmt.close();
			// conn.close();
		} catch (SQLException e) {
			System.out.println("error:" + e.getMessage());
		}
		return t;
	}

	public boolean update(String sql) {
		boolean result = false;
		if (sql == null || sql.equals(""))
			return result;

		Connection conn;
		Statement smt;
		try {
			conn = getConnection();
			smt = conn.createStatement();
			result = smt.executeUpdate(sql) != -1;
			smt.close();
			// conn.close();
		} catch (SQLException e) {
			System.out.println("error:" + e.getMessage() + "\r\n:sql:" + sql);
		}

		return result;
	}

	public boolean executeBatch(List<String> arrSql) {
		boolean result = false;
		if (arrSql == null || arrSql.size() < 1)
			return result;
		Connection conn;
		Statement smt;
		try {
			conn = getConnection();
			smt = conn.createStatement();
			for (String sql : arrSql) {
				smt.addBatch(sql);
			}
			smt.executeBatch();
			smt.close();
			// conn.close();
			result = true;
		} catch (SQLException e) {
			System.out.println("error:" + e.getMessage());
		}

		return result;
	}

	public boolean executeBatch(String[] arrSql) {
		boolean result = false;
		if (arrSql == null || arrSql.length < 1)
			return result;
		Connection conn;
		Statement smt;
		try {
			conn = getConnection();
			smt = conn.createStatement();
			for (String sql : arrSql) {
				smt.addBatch(sql);
			}
			smt.executeBatch();
			smt.close();
			// conn.close();
			result = true;
		} catch (SQLException e) {
			System.out.println("error:" + e.getMessage());
		}

		return result;

	}

	public boolean executeBatchAndTransaction(List<String> arrSql) {
		boolean result = false;
		if (arrSql == null || arrSql.size() < 1)
			return result;

		Connection conn = null;
		Statement smt;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			smt = conn.createStatement();
			for (String sql : arrSql) {
				smt.addBatch(sql);
			}
			smt.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
			smt.close();
			// conn.close();
			result = true;
		} catch (SQLException e) {
			System.out.println("error:" + e.getMessage());
			try {
				conn.rollback();
				// conn.close();
			} catch (SQLException e1) {
				System.out.println("error:" + e1.getMessage());
			}

			System.out.println("error:" + e.getMessage());
		}

		return result;
	}

	public boolean executeBatchAndTransaction(String[] arrSql) {
		boolean result = false;
		if (arrSql == null || arrSql.length < 1)
			return result;

		Connection conn = null;
		Statement smt;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			smt = conn.createStatement();
			for (String sql : arrSql) {
				smt.addBatch(sql);
			}
			smt.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
			smt.close();
			// conn.close();
			result = true;
		} catch (SQLException e) {
			try {
				conn.rollback();
				// conn.close();
			} catch (SQLException e1) {
				System.out.println("error:" + e1.getMessage());
			}

			System.out.println("error:" + e.getMessage());
		}

		return result;
	}

	public Map<String, Object> query(String sql) {

		if (sql == null || sql.equals(""))
			return null;

		Map<String, Object> map = null;
		Connection conn;
		Statement smt;
		ResultSet rs;
		try {
			conn = getConnection();
			smt = conn.createStatement();
			rs = smt.executeQuery(sql);
			ResultSetMetaData md = rs.getMetaData();
			int num = md.getColumnCount();
			if (rs.next()) {
				map = new HashMap<>(num);
				for (int i = 1; i <= num; i++) {
					map.put(md.getColumnName(i).toLowerCase(), rs.getObject(i));
				}
			}
			rs.close();
			smt.close();
			// conn.close();
		} catch (SQLException e) {
			System.out.println("error:" + e.getMessage());
		}
		return map == null ? new HashMap<String, Object>() : map;
	}
	
	


	public Map<String, Object> query(String sql, Object[] obj) {

		if (sql == null || sql.equals(""))
			return null;

		if (obj != null && obj.length > 0) {
			StringBuilder sb = new StringBuilder();
			String[] arrSql = sql.split("\\?");
			for (int i = 0; i < arrSql.length; i++) {
				sb.append(arrSql[i]);
				if (i <= (obj.length - 1))
					sb.append(obj[i]);
			}
			sql = sb.toString();
		}

		Map<String, Object> map = null;
		Connection conn;
		Statement smt;
		ResultSet rs;
		try {
			conn = getConnection();
			smt = conn.createStatement();
			rs = smt.executeQuery(sql);
			ResultSetMetaData md = rs.getMetaData();
			int num = md.getColumnCount();
			if (rs.next()) {
				map = new HashMap<>(num);
				for (int i = 1; i <= num; i++) {
					map.put(md.getColumnName(i).toLowerCase(), rs.getObject(i));
				}
			}
			rs.close();
			smt.close();
			// conn.close();
		} catch (SQLException e) {
			System.out.println("error:" + e.getMessage());
		}

		return map == null ? new HashMap<String, Object>() : map;
	}

	public <T> T query(String sql, EntityMapper<?> mapper) {

		return query(sql, null, mapper);
	}

	@SuppressWarnings("unchecked")
	public <T> T query(String sql, Object[] obj, EntityMapper<?> mapper) {

		if (sql == null || sql.equals(""))
			return null;
		if (mapper == null)
			return null;
		if (obj != null && obj.length > 0) {
			StringBuilder sb = new StringBuilder();
			String[] arrSql = sql.split("\\?");
			for (int i = 0; i < arrSql.length; i++) {
				sb.append(arrSql[i]);
				if (i <= (obj.length - 1))
					sb.append(obj[i]);
			}
			sql = sb.toString();
		}

		T t = null;
		Connection conn;
		Statement smt;
		ResultSet rs;
		try {
			conn = getConnection();
			smt = conn.createStatement();
			rs = smt.executeQuery(sql);
			if (rs.next()) {
				t = (T) mapper.Mapper(rs);
			}
			rs.close();
			smt.close();
			// conn.close();
		} catch (SQLException e) {
			System.out.println("error:" + e.getMessage());
		}
		return t;
	}

	public List<Map<String, Object>> queryForList(String sql) {

		if (sql == null || sql.equals(""))
			return null;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Connection conn;
		Statement smt;
		ResultSet rs;
		try {
			conn = getConnection();
			smt = conn.createStatement();
			rs = smt.executeQuery(sql);
			ResultSetMetaData md = rs.getMetaData();
			int num = md.getColumnCount();

			while (rs.next()) {
				Map<String, Object> map = new HashMap<>(num);
				for (int i = 1; i <= num; i++) {
					map.put(md.getColumnName(i).toLowerCase(), rs.getObject(i));
				}
				list.add(map);
			}
			rs.close();
			smt.close();
			// conn.close();
		} catch (SQLException e) {
			System.out.println("error:" + e.getMessage());
		}

		return list == null ? new ArrayList<Map<String, Object>>() : list;
	}

	public List<Map<String, Object>> queryForList(String sql, Object[] obj) {

		if (sql == null || sql.equals(""))
			return null;

		if (obj != null && obj.length > 0) {
			StringBuilder sb = new StringBuilder();
			String[] arrSql = sql.split("\\?");
			for (int i = 0; i < arrSql.length; i++) {
				sb.append(arrSql[i]);
				if (i <= (obj.length - 1))
					sb.append(obj[i]);
			}
			sql = sb.toString();
		}

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn;
		Statement smt;
		ResultSet rs;
		try {
			conn = getConnection();
			smt = conn.createStatement();
			rs = smt.executeQuery(sql);
			ResultSetMetaData md = rs.getMetaData();
			int num = md.getColumnCount();

			while (rs.next()) {
				Map<String, Object> map = new HashMap<>(num);
				for (int i = 1; i <= num; i++) {
					map.put(md.getColumnName(i).toLowerCase(), rs.getObject(i));
				}
				list.add(map);
			}
			rs.close();
			smt.close();
			// conn.close();
		} catch (SQLException e) {
			System.out.println("error:" + e.getMessage());
		}

		return list == null ? new ArrayList<Map<String, Object>>() : list;

	}

	public <T> List<T> queryForList(String sql, EntityMapper<?> mapper) {
		return queryForList(sql, null, mapper);
	}

	public <T> List<T> queryForList(String sql, Object[] obj, EntityMapper<?> mapper) {

		if (sql == null || sql.equals(""))
			return null;
		if (mapper == null)
			return null;
		if (obj != null && obj.length > 0) {
			StringBuilder sb = new StringBuilder();
			String[] arrSql = sql.split("\\?");
			for (int i = 0; i < arrSql.length; i++) {
				sb.append(arrSql[i]);
				if (i <= (obj.length - 1))
					sb.append(obj[i]);
			}
			sql = sb.toString();
		}

		List<T> list = new ArrayList<>();

		Connection conn;
		Statement smt;
		ResultSet rs;
		try {
			conn = getConnection();
			smt = conn.createStatement();
			rs = smt.executeQuery(sql);
			while (rs.next()) {
				@SuppressWarnings("unchecked")
				T t = (T) mapper.Mapper(rs);

				if (t != null)
					list.add(t);
			}
			rs.close();
			smt.close();
			// conn.close();
		} catch (SQLException e) {
			System.out.println("error:" + e.getMessage());
		}

		return list == null ? new ArrayList<T>() : list;

	}

	public boolean exits(String sql) {
		int count = 0;
		Connection conn;
		Statement smt;
		ResultSet rs;
		try {
			conn = getConnection();
			smt = conn.createStatement();
			rs = smt.executeQuery(sql);
			if (rs.next())
				count = rs.getInt(1);
			rs.close();
			smt.close();
			// conn.close();
		} catch (SQLException e) {
			System.out.println("error:" + e.getMessage());
		}
		return count > 0;
	}

	@SuppressWarnings("unchecked")
	public <T> T selectMax(String sql, EntityMapper<?> mapper) {
		T t = null;
		Connection conn;
		Statement smt;
		ResultSet rs;
		try {
			conn = getConnection();
			smt = conn.createStatement();
			rs = smt.executeQuery(sql);
			if (rs.next())
				t = (T) mapper.Mapper(rs);
			rs.close();
			smt.close();
			// conn.close();
		} catch (SQLException e) {
			System.out.println("error:" + e.getMessage());
		}

		return t;
	}

	public EntityPager<Map<String, Object>> queryForPageList(String tableName, int pageIndex, int pageSize) {
		return queryForPageList(tableName, null, null, null, pageIndex, pageSize);
	}

	public EntityPager<Map<String, Object>> queryForPageList(String tableName, String fieldNames, int pageIndex,
			int pageSize) {
		return queryForPageList(tableName, fieldNames, null, null, pageIndex, pageSize);
	}

	public EntityPager<Map<String, Object>> queryForPageList(String tableName, String fieldNames, String where,
			int pageIndex, int pageSize) {
		return queryForPageList(tableName, fieldNames, where, null, pageIndex, pageSize);
	}

	public EntityPager<Map<String, Object>> queryForPageList(String tableName, String fieldNames, String where,
			String order, int pageIndex, int pageSize) {
		String[] arrSql = getQueryEntityForPageListSql(tableName, fieldNames, where, order, pageIndex, pageSize);
		if (arrSql.length < 1)
			return null;
		if (arrSql[0] == null || arrSql[0].equals(""))
			return null;
		if (arrSql[1] == null || arrSql[1].equals(""))
			return null;
		String countSql = arrSql[0];
		String querySql = arrSql[1];

		EntityPager<Map<String, Object>> entity = new EntityPager<Map<String, Object>>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		int rowCount = 0;
		int pageCount = 1;
		Connection conn;
		Statement smt;
		ResultSet countRs;
		ResultSet rs;
		try {
			conn = getConnection();
			smt = conn.createStatement();
			countRs = smt.executeQuery(countSql);
			if (countRs != null && countRs.next())
				rowCount = countRs.getInt(1);
			countRs.close();
			smt.close();

			pageCount = (int) Math.ceil(rowCount * 1.0 / pageSize);
			smt = conn.createStatement();
			rs = smt.executeQuery(querySql);

			ResultSetMetaData md = rs.getMetaData();
			int num = md.getColumnCount();

			while (rs.next()) {
				Map<String, Object> map = new HashMap<>(num);
				for (int i = 1; i <= num; i++) {
					map.put(md.getColumnName(i).toLowerCase(), rs.getObject(i));
				}
				list.add(map);
			}

			rs.close();
			smt.close();
			// conn.close();
		} catch (SQLException e) {
			System.out.println("error:" + e.getMessage());
		}

		if (pageCount < 1)
			pageCount = 1;

		entity.setPageCount(pageCount);
		entity.setPageIndex(pageIndex);
		entity.setPageSize(pageSize);
		entity.setRowCount(rowCount);
		entity.setTotal(rowCount);
		entity.setRows(list);
		return entity;

	}

	public <T> EntityPager<T> queryEntityForPageList(String tableName, int pageIndex, int pageSize,
			EntityMapper<?> mapper) {
		return queryEntityForPageList(tableName, null, null, null, pageIndex, pageSize, mapper);
	}

	public <T> EntityPager<T> queryEntityForPageList(String tableName, String fieldNames, int pageIndex, int pageSize,
			EntityMapper<?> mapper) {
		return queryEntityForPageList(tableName, fieldNames, null, null, pageIndex, pageSize, mapper);
	}

	public <T> EntityPager<T> queryEntityForPageList(String tableName, String fieldNames, String where, int pageIndex,
			int pageSize, EntityMapper<?> mapper) {
		return queryEntityForPageList(tableName, fieldNames, where, null, pageIndex, pageSize, mapper);
	}

	private String[] getQueryEntityForPageListSql(String tableName, String fieldNames, String where, String order,
			int pageIndex, int pageSize) {
		if (tableName == null || tableName.equals(""))
			return new String[] {};

		if (fieldNames == null || fieldNames.equals(""))
			fieldNames = "*";
		if (where == null)
			where = "";
		if (order == null)
			order = "";
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageSize < 1)
			pageSize = 5;

		String countSql = "";
		String querySql = "";
		if (isMariadb()) {
			countSql = "select count(*) from " + tableName;
			querySql = "select " + fieldNames + " from " + tableName;
			if (!where.equals("")) {
				countSql += " where " + where;
				querySql += " where " + where;
			}

			if (!order.equals(""))
				querySql += " order by " + order;
			querySql += " LIMIT " + ((pageIndex - 1) * pageSize) + "," + pageSize;
		}

		if (isOracle()) {
			countSql = "select count(*) from " + tableName;

			querySql = "SELECT * FROM (SELECT ROWNUM AS rowno,r.* " + "  FROM(SELECT " + fieldNames + " FROM "
					+ tableName;
			if (!where.equals("")) {
				querySql += " WHERE " + where;
				countSql += " WHERE " + where;
			}

			if (!order.equals(""))
				querySql += " ORDER BY " + order;

			querySql += "  ) r ";
			querySql += "  where ROWNUM <= " + (pageIndex * pageSize);
			querySql += "  ) table_alias ";
			querySql += "WHERE table_alias.rowno > " + ((pageIndex - 1) * pageSize);

		}

		return new String[] { countSql, querySql };

	}

	public <T> EntityPager<T> queryEntityForPageList(String tableName, String fieldNames, String where, String order,
			int pageIndex, int pageSize, EntityMapper<?> mapper) {

		if (mapper == null)
			return null;
		String[] arrSql = getQueryEntityForPageListSql(tableName, fieldNames, where, order, pageIndex, pageSize);
		if (arrSql.length < 1)
			return null;
		if (arrSql[0] == null || arrSql[0].equals(""))
			return null;
		if (arrSql[1] == null || arrSql[1].equals(""))
			return null;

		String countSql = arrSql[0];
		String querySql = arrSql[1];
		System.out.println("打印SQL：" + querySql);
		EntityPager<T> entity = new EntityPager<>();
		List<T> list = new ArrayList<>();
		int rowCount = 0;
		int pageCount = 1;
		Connection conn;
		Statement smt;
		ResultSet countRs;
		ResultSet rs;
		try {
			conn = getConnection();
			smt = conn.createStatement();
			countRs = smt.executeQuery(countSql);
			if (countRs != null && countRs.next())
				rowCount = countRs.getInt(1);
			countRs.close();
			smt.close();

			pageCount = (int) Math.ceil(rowCount * 1.0 / pageSize);
			smt = conn.createStatement();
			rs = smt.executeQuery(querySql);
			while (rs != null && rs.next()) {
				@SuppressWarnings("unchecked")
				T t = (T) mapper.Mapper(rs);
				list.add(t);
			}
			rs.close();
			smt.close();
			// conn.close();
		} catch (SQLException e) {
			System.out.println("error:" + e.getMessage());
		}

		if (pageCount < 1)
			pageCount = 1;

		entity.setPageCount(pageCount);
		entity.setPageIndex(pageIndex);
		entity.setPageSize(pageSize);
		entity.setRowCount(rowCount);
		entity.setTotal(rowCount);
		entity.setRows(list);

		return entity;

	}

	public EntityPager<Map<String, Object>> queryForPageList(int pageIndex, int pageSize, String countSql,
			String querySql) {
		if (countSql == null || countSql.equals(""))
			return null;
		if (querySql == null || querySql.equals(""))
			return null;

		querySql += " LIMIT " + ((pageIndex - 1) * pageSize) + "," + pageSize;

		if (pageIndex < 1)
			pageIndex = 1;
		if (pageSize < 1)
			pageSize = 5;
		EntityPager<Map<String, Object>> entity = new EntityPager<>();
		List<Map<String, Object>> list = new ArrayList<>();
		int rowCount = 0;
		int pageCount = 1;
		Connection conn;
		Statement smt;
		ResultSet countRs;
		ResultSet rs;
		try {
			conn = getConnection();
			smt = conn.createStatement();
			countRs = smt.executeQuery(countSql);
			if (countRs != null && countRs.next())
				rowCount = countRs.getInt(1);
			countRs.close();
			smt.close();
			pageCount = (int) Math.ceil(rowCount * 1.0 / pageSize);

			smt = conn.createStatement();
			rs = smt.executeQuery(querySql);
			ResultSetMetaData md = rs.getMetaData();
			int num = md.getColumnCount();

			while (rs.next()) {
				Map<String, Object> map = new HashMap<>(num);
				for (int i = 1; i <= num; i++) {
					map.put(md.getColumnName(i).toLowerCase(), rs.getObject(i));
				}
				list.add(map);
			}
			rs.close();
			smt.close();
			// conn.close();
		} catch (SQLException e) {
			System.out.println("error:" + e.getMessage());
		}

		if (pageCount < 1)
			pageCount = 1;

		entity.setPageCount(pageCount);
		entity.setPageIndex(pageIndex);
		entity.setPageSize(pageSize);
		entity.setRowCount(rowCount);
		entity.setRows(list);
		entity.setTotal(rowCount);
		return entity;
	}

	public <T> EntityPager<T> queryForPageList(int pageIndex, int pageSize, String countSql, String querySql,
			EntityMapper<?> mapper) {
		if (countSql == null || countSql.equals(""))
			return null;
		if (querySql == null || querySql.equals(""))
			return null;

		querySql += " LIMIT " + ((pageIndex - 1) * pageSize) + "," + pageSize;

		if (pageIndex < 1)
			pageIndex = 1;
		if (pageSize < 1)
			pageSize = 5;
		EntityPager<T> entity = new EntityPager<>();
		List<T> list = new ArrayList<>();
		int rowCount = 0;
		int pageCount = 1;
		Connection conn;
		Statement smt;
		ResultSet countRs;
		ResultSet rs;
		try {
			conn = getConnection();
			smt = conn.createStatement();
			countRs = smt.executeQuery(countSql);
			if (countRs != null && countRs.next())
				rowCount = countRs.getInt(1);
			countRs.close();
			smt.close();
			pageCount = (int) Math.ceil(rowCount * 1.0 / pageSize);

			smt = conn.createStatement();
			rs = smt.executeQuery(querySql);
			while (rs != null && rs.next()) {
				@SuppressWarnings("unchecked")
				T t = (T) mapper.Mapper(rs);
				list.add(t);
			}
			rs.close();
			smt.close();
			// conn.close();
		} catch (SQLException e) {
			System.out.println("error:" + e.getMessage());
		}

		if (pageCount < 1)
			pageCount = 1;

		entity.setPageCount(pageCount);
		entity.setPageIndex(pageIndex);
		entity.setPageSize(pageSize);
		entity.setRowCount(rowCount);
		entity.setRows(list);
		entity.setTotal(rowCount);
		return entity;
	}

	/**************************************************/
	// 以下为过期方法不在使用
	@Deprecated
	public boolean exitsId(String tableName, String where) {
		return exits(tableName, "id", where);
	}

	@Deprecated
	public boolean exits(String tableName, String pk, String where) {

		if (pk == null || pk.equals(""))
			pk = "*";
		if (tableName == null || tableName.equals(""))
			return true;
		String commandText = "";
		if (isMariadb()) {
			commandText = "select count(" + pk + ") as c from " + tableName;
			if (where != null && !where.equals(""))
				commandText += " where " + where;
		}
		if (isOracle()) {
			commandText = "select count(" + pk + ") as c from " + tableName;
			if (where != null && !where.equals(""))
				commandText += " where " + where;
		}
		return exits(commandText);
	}

	@Deprecated
	@SuppressWarnings({ "unchecked" })
	public <T> T selectMax(String tableName, String filed, String where, EntityMapper<?> mapper) {

		String sql = "select max(" + filed + ") as m from " + tableName;
		if (where != null && !where.equals(""))
			sql += " where " + where;
		T t = null;
		Connection conn;
		Statement smt;
		ResultSet rs;
		try {
			conn = getConnection();
			smt = conn.createStatement();
			rs = smt.executeQuery(sql);
			if (rs.next())
				t = (T) mapper.Mapper(rs);
			rs.close();
			smt.close();
			// conn.close();
		} catch (SQLException e) {
			System.out.println("error:" + e.getMessage());
		}

		return t;

	}

	@Deprecated
	public <T> T queryEntity(String tableName, EntityMapper<?> mapper) {
		return queryEntity(tableName, null, null, mapper);
	}

	@Deprecated
	public <T> T queryEntity(String tableName, String where, EntityMapper<?> mapper) {
		return queryEntity(tableName, where, null, mapper);
	}

	@Deprecated
	private String getQueryEntitySql(String tableName, String where, String order) {

		String commandText = "";
		if (isMariadb()) {
			commandText = "select * from " + tableName;
			if (where != null && !where.equals(""))
				commandText += " where " + where;
			if (order != null && !order.equals(""))
				commandText += " order by " + order;
			commandText += " limit 1";
		}

		if (isOracle()) {
			commandText = "select * from " + tableName;
			if (where != null && !where.equals(""))
				commandText += " where " + where;
			if (order != null && !order.equals(""))
				commandText += " order by " + order;
		}

		return commandText;

	}

	@Deprecated
	@SuppressWarnings("unchecked")
	public <T> T queryEntity(String tableName, String where, String order, EntityMapper<?> mapper) {

		if (tableName == null || tableName.equals(""))
			return null;

		String sql = getQueryEntitySql(tableName, where, order);

		if (sql == null || sql.equals(""))
			return null;

		T t = null;
		Connection conn;
		Statement smt;
		ResultSet rs;
		try {
			conn = getConnection();
			smt = conn.createStatement();
			rs = smt.executeQuery(sql);
			if (rs.next())
				t = (T) mapper.Mapper(rs);
			rs.close();
			smt.close();
			// conn.close();
		} catch (SQLException e) {
			System.out.println("error:" + e.getMessage());
		}

		return t;

	}

	@Deprecated
	public <T> List<T> queryEntityForList(String tableName, EntityMapper<?> mapper) {
		return queryEntityForList(tableName, null, null, null, mapper);
	}

	@Deprecated
	public <T> List<T> queryEntityForList(String tableName, String fieldNames, EntityMapper<?> mapper) {
		return queryEntityForList(tableName, fieldNames, null, null, mapper);
	}

	@Deprecated
	public <T> List<T> queryEntityForList(String tableName, String fieldNames, String where, EntityMapper<?> mapper) {
		return queryEntityForList(tableName, fieldNames, where, null, mapper);
	}

	@Deprecated
	private String getQueryEntityForListSql(String tableName, String fieldNames, String where, String order) {

		if (tableName == null || tableName.equals(""))
			return "";

		if (fieldNames == null)
			fieldNames = "*";
		if (where == null)
			where = "";
		if (order == null)
			order = "";

		String commandText = "";

		if (isMariadb()) {

			commandText = "select " + fieldNames + " from " + tableName;
			if (!where.equals(""))
				commandText += " where " + where;
			if (!order.equals(""))
				commandText += " order by " + order;
		}

		if (isOracle()) {
			commandText = "select " + fieldNames + " from " + tableName;
			if (!where.equals(""))
				commandText += " where " + where;
			if (!order.equals(""))
				commandText += " order by " + order;
		}

		return commandText;
	}

	@Deprecated
	public <T> List<T> queryEntityForList(String tableName, String fieldNames, String where, String order,
			EntityMapper<?> mapper) {

		if (mapper == null)
			return null;

		String sql = getQueryEntityForListSql(tableName, fieldNames, where, order);
		if (sql == null || sql.equals(""))
			return null;

		List<T> list = new ArrayList<>();

		Connection conn;
		Statement smt;
		ResultSet rs;

		try {
			conn = getConnection();
			smt = conn.createStatement();
			rs = smt.executeQuery(sql);
			while (rs.next()) {
				@SuppressWarnings("unchecked")
				T t = (T) mapper.Mapper(rs);
				list.add(t);
			}
			rs.close();
			smt.close();
			// conn.close();
		} catch (SQLException e) {
			System.out.println("error:" + e.getMessage());
		}

		return list;
	}

}
