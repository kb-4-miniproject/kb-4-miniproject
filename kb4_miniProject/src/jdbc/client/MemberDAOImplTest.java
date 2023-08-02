package jdbc.client;

import config.ServerInfo;
import jdbc.dao.impl.MemberDAOImpl;
import jdbc.dto.Member;

public class MemberDAOImplTest {

	public static void main(String[] args) throws Exception {
		MemberDAOImpl dao = MemberDAOImpl.getInstance();
		dao.insertMember(new Member(40, "수현", "meri971103@gmail.com", "010-8937-4030"));
//		dao.deleteMember(20);
//		dao.updateMember(new Member(20, "메리", "kanghl1111@naver.com" , "010-7296-2003"));
//		dao.getMember(10);
//		dao.getMember();
	}	
	static {
		try {
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("Driver Loading Success....");
		}catch(ClassNotFoundException e) {
			System.out.println("Driver Loading Fail....");
		}
	}

	}


