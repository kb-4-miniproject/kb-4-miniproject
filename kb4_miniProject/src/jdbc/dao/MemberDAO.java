package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jdbc.dto.Member;

public interface MemberDAO {
	// 공통 로직
	// 오류: Impl의 private ~ throws SQLException과 양립 불가
//	Connection getConnect();
//	void closeAll(Connection conn, PreparedStatement ps);
//	void closeAll(Connection conn, PreparedStatement ps, ResultSet rs);
	
	// 데이터 액세스 로직 (CRUD)
	void  insertMember(Member m) throws SQLException;  // C
	void  deleteMember(int id) throws SQLException;  // D
	void  updateMember(Member m)throws SQLException;  // U
	
	Member getMember(int id) throws SQLException;  // R
	ArrayList<Member> getMember() throws SQLException;
	ArrayList<Member> getMember(String name) throws SQLException;
}
