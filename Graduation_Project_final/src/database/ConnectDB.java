package database;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cryption.CipherFunc;



public class ConnectDB {

	static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	static final String DB_URL = "jdbc:mariadb://localhost:3306/classification";
	static final String USER = "root";
	static final String PASS = "1234";
	Connection conn;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;

	CipherFunc cipher;

	public ConnectDB() {
		try {
			cipher = new CipherFunc();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		conn = null;
		stmt = null;
		rs = null;

		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Load Memory.....");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("connect to DB.....");
			if (conn != null) {
				System.out.println("성공");
			} else {
				System.out.println("실패");
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	public void endDB() {

		try {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void insertDTO(String name, String price) {
		String sql = "insert into classification_db(id,classification,orderClass) values(NULL,?,?);";
		PreparedStatement pstmt = null;
		try {
			DTO DBObject = new DTO(cipher.encrypt(name), cipher.encrypt(price));
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, DBObject.getClassification());
			pstmt.setString(2, DBObject.getOrder());
			pstmt.executeUpdate();
		} catch (SQLException | UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteDTO(int id) {
		String sql = "delete from classification_db where id = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				sql = "ALTER TABLE classification_db AUTO_INCREMENT=1;";
				pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();
				sql = "SET @COUNT = 0;";
				pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();
				sql = "UPDATE classification_db SET id = @COUNT:=@COUNT+1;";
				pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public DTO selectOne(String tag, String value) {
		String sql = null;
		PreparedStatement pstmt = null;
		DTO re = null;
		ResultSet rs = null;
		
		switch (tag) {
		
		case "ID":
			try {
				
				sql = "select * from classification_db where "+tag.toLowerCase()+" = ?;";
				pstmt = null;
				re = new DTO();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, Integer.valueOf(value));
				rs = pstmt.executeQuery();

				if (rs.next()) {
					re.setID(rs.getInt("id"));
					re.setClassification(rs.getString("classification"));
					re.setOrder(rs.getString("orderClass"));
				}

			} catch (SQLException e1) {
				return null;
			} finally {
				try {
					if (pstmt != null && !pstmt.isClosed())
						pstmt.close();
				} catch (SQLException e) {
					return null;

				}
			}

			break;
		case "classification":
		case "orderClass":
			try {
				sql = "select * from classification_db where "+tag.toLowerCase()+" = ?;";
				pstmt = null;
				re = new DTO();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, cipher.encrypt(value));
				rs = pstmt.executeQuery();

				if (rs.next()) {
					re.setID(rs.getInt("id"));
					re.setClassification(rs.getString("classification"));
					re.setOrder(rs.getString("orderClass"));
				}
			} catch (SQLException | UnsupportedEncodingException | GeneralSecurityException e1) {
				return null;
			} finally {
				try {
					if (pstmt != null && !pstmt.isClosed())
						pstmt.close();
				} catch (SQLException e) {
					return null;
				}
			}

			break;
		
		}

		return re;
	}

	public List<DTO> selectAll() {
		String sql = "select * from classification_db;";
		PreparedStatement pstmt = null;

		List<DTO> list = new ArrayList<DTO>();

		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet re = pstmt.executeQuery();

			while (re.next()) {
				DTO s = new DTO();
				s.setID(re.getInt("id"));
				s.setClassification(re.getString("classification"));
				s.setOrder(re.getString("orderClass"));
				list.add(s);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	public void updateDTO(DTO DBObject) {
		String sql = "update classification_db set classification=?, orderClass=? where id = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, DBObject.getClassification());
			pstmt.setString(2, DBObject.getOrder());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
