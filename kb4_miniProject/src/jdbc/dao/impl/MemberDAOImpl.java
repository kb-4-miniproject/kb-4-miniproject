package jdbc.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import config.ServerInfo;
import jdbc.dao.MemberDAO;
import jdbc.dto.Member;
import jdbc.exception.DuplicateIDException;
import jdbc.exception.RecordNotFoundException;

public class MemberDAOImpl implements MemberDAO{
	// 싱글톤 패턴 구현 
	private static MemberDAOImpl dao = new MemberDAOImpl();
	private MemberDAOImpl(){
		System.out.println("MemberDAOImpl Creating...Using Singletone");
	}
	public static MemberDAOImpl getInstance() {
		return dao;
	}
	
	// 공통 로직
	private Connection getConnect() throws SQLException{  // DB 연결 
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD);
		System.out.println("DB Connect....");
		
		return conn;
	}	
	private void closeAll(Connection conn, PreparedStatement ps) throws SQLException{  // conn, ps 닫기 
		if(ps!=null) ps.close();
		if(conn!=null) conn.close();
	}	
	private void closeAll(Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException{  // conn, ps, rs 닫기 
		if(rs!=null) rs.close();
		closeAll(conn,ps);
	}
	
	// 데이터 액세스 로직 (CRUD)
	public boolean idExists(int id, Connection conn)throws SQLException{  // 기존에 존재하는 사용자인지 확인
		PreparedStatement ps = null;
		ResultSet rs = null;
			String query = "SELECT id FROM member WHERE id=?";
			ps=  conn.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			return rs.next();
			
	}
	
	@Override
	public void insertMember(Member m) throws SQLException {		
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();

			if(!idExists(m.getId(), conn)) { //id가 없다면...추가진행
				String query = 
						"INSERT INTO member (id, name, email, phone) VALUES(seq_id.nextVal,?,?,?)";
				ps = conn.prepareStatement(query);
				ps.setInt(1, m.getId());
				ps.setString(2, m.getName());
				ps.setString(3, m.getEmail());
				ps.setString(4, m.getPhone());
				
				System.out.println(ps.executeUpdate()+" 명 회원 등록~~!!");
			}else {
				if (idExists(m.getId(), conn)) new DuplicateIDException();
				System.out.println(m.getName()+"님은 이미 회원이십니다.!!");
			}			
		}finally {
			closeAll(conn, ps);
		}
	}
	
	@Override
	public void deleteMember(int id) throws SQLException {		
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (idExists(id, conn)) {
				conn = getConnect();
				String query = "DELETE member WHERE id =?";
				ps = conn.prepareStatement(query);
				ps.setInt(1, id);
				int result = ps.executeUpdate();
				System.out.println(result + " 명 삭제 성공!");
			} else {
				if (!idExists(id, conn)) new RecordNotFoundException();
				System.out.println("회원을 찾을 수 없습니다!");
			}
		} finally {
		ps.close();
		conn.close();
		}
	}
	
	@Override
	public void updateMember(Member m) throws SQLException {		
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (idExists(m.getId(), conn)) {
				conn = getConnect();
				String query = "UPDATE member SET name=?, email=?, phone=? WHERE id=?";

				ps = conn.prepareStatement(query);
				ps.setString(1, m.getName());
				ps.setString(2, m.getEmail());
				ps.setString(3, m.getPhone());
				ps.setInt(4, m.getId());
				int result = ps.executeUpdate();
				System.out.println(result + " 명 업데이트 성공!");
			} else {
				if (!idExists(m.getId(), conn)) new RecordNotFoundException();
				System.out.println("회원을 찾을 수 없습니다!");
			}
		} finally {
		ps.close();
		conn.close();
		}
	}
	
	@Override
	public Member getMember(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
			String query = "SELECT id, name, email, phone FROM member WHERE id = ?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			// 지금은 행이 1개이므로 next()로 while 돌릴 필요 없음 -> if 문으로 대체
			if (rs.next()) {
				System.out.println(rs.getInt("id") + "\t"
								   + rs.getString("name")+ "\t"
								   + rs.getString("email")+ "\t"
								   + rs.getString("phone"));
			}
		} finally {
			closeAll(conn, ps, rs);  // rs도 닫아줘야하므로 오버로딩 사용
		}
		return null;
	}

	@Override
	public ArrayList<Member> getMember() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
			String query = "SELECT id, name, email, phone FROM member";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			// 지금은 행이 여러개이므로 next()로 while 돌리기
			while(rs.next()) {
				System.out.println(rs.getInt("id") + "\t"
						   + rs.getString("name")+ "\t"
						   + rs.getString("email")+ "\t"
						   + rs.getString("phone"));
			}
		} finally {
			closeAll(conn, ps, rs);  // rs도 닫아줘야하므로 오버로딩 사용
		}
		return null;
	}
	
	@Override
	public ArrayList<Member> getMember(String name) throws SQLException {
	
		return null;
	}
}
