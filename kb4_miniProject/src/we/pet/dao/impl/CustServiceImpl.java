package we.pet.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import config.ServerInfo;
import we.pet.dao.CustService;
import we.pet.exception.DuplicateIdException;
import we.pet.exception.InvalidTransactionException;
import we.pet.exception.RecordNotFoundException;
import we.pet.vo.Cart;
import we.pet.vo.Customer;
import we.pet.vo.Pet;
import we.pet.vo.PetFood;

public class CustServiceImpl implements CustService{
	// 싱글톤 패턴 구현
	private static CustServiceImpl cs = new CustServiceImpl();
	private CustServiceImpl(){
		System.out.println("CustServiceImpl Creating...Using Singletone");
	}
	public static CustServiceImpl getInstance() {
		return cs;
	}
	public CustServiceImpl(String serverIp) throws ClassNotFoundException {
	Class.forName(ServerInfo.DRIVER_NAME);
	System.out.println("driver loading");
}
	
	// 공통로직
		@Override
	public Connection getConnect() throws SQLException {
			Connection conn = DriverManager.getConnection(ServerInfo.URL,ServerInfo.USER,ServerInfo.PASSWORD);
			System.out.println("connected");
			return conn;
		}
		@Override
	public void closeAll(PreparedStatement ps, Connection conn) throws SQLException {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
			System.out.println("all closed");
			
		}
		@Override
	public void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {
			if(rs!=null) rs.close();
			closeAll(ps,conn);
			
		}
		
	CustDAOImpl dao = CustDAOImpl.getInstance();
		
	@Override
    public PetFood recommPetFoodByBudget(Pet pet, int budget) throws SQLException {  //다시 짜야함: 신장병 있는 고양이는 단백질 낮게
        PetFood pf = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs=  null;
       
        
        try {
            conn = dao.getConnect();
            
            String query ="";
            
            if(pet.getSpecies() == 0) { // 고양이
                if (pet.isKidney()) {// 신장병 있음
                    query = "SELECT id, name, species, price, stock, texture, satisfaction, calories, "
                            + "DECODE(protein, '1', '고단백', '0', '저단백') protein "
                            + "FROM ( "
                            + "SELECT * "
                            + "FROM pet_food "
                            + "WHERE protein = 0 AND " // 단백질 X
                            + "price < ? "
                            + "ORDER BY satisfaction) "
                            + "WHERE ROWNUM = 1";
                }else {
                    query = "SELECT id, name, species, price, stock, texture, satisfaction, calories, "
                            + "DECODE(protein, '1', '고단백', '0', '저단백') protein "
                            + "FROM ( "
                            + "SELECT * "
                            + "FROM pet_food "
                            + "WHERE protein = 1 AND " // 단백질 X
                            + "price < ? "
                            + "ORDER BY satisfaction) "
                            + "WHERE ROWNUM = 1";
                }
            } else {// 강아지
                if (pet.isWalktime()) {// 많이 걸음
                    query = "SELECT id, name, species, price, stock, texture, satisfaction, calories, "
                            + "DECODE(walktime, '1', '고단백', '0', '저단백') protein "
                            + "FROM ( "
                            + "SELECT * "
                            + "FROM pet_food "
                            + "WHERE walktime = 1 AND " // 단백질 X
                            + "price < ? "
                            + "ORDER BY satisfaction) "
                            + "WHERE ROWNUM = 1";
                    
                }else { // 많이 안 걸음
                    query = "SELECT id, name, species, price, stock, texture, satisfaction, calories, "
                            + "DECODE(walktime, '1', '고단백', '0', '저단백') protein "
                            + "FROM ( "
                            + "SELECT * "
                            + "FROM pet_food "
                            + "WHERE walktime = 0 AND " // 단백질 X
                            + "price < ? "
                            + "ORDER BY satisfaction) "
                            + "WHERE ROWNUM = 1";
                }
            }
            
           
            ps = conn.prepareStatement(query);
            ps.setInt(1, budget);
            rs = ps.executeQuery();
            /* int id, String name, int price, int stock, boolean species, 
             * int texture, double satisfaction, boolean protein, boolean calories
             */
            rs.next();
            pf = new PetFood(
                    
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getInt("stock"),
                    rs.getBoolean("species"),
                    rs.getInt("texture"),
                    rs.getDouble("satisfaction")
//                    rs.getBoolean("protein"),
//                    rs.getBoolean("calories")
                    );
            
            System.out.println(pf);
            return pf; 
            
            
        } finally {
            dao.closeAll(rs, ps, conn);
        } 
    }

	@Override
	public PetFood recommPetFoodByTexture(Pet pet, int texture) throws SQLException {
		PetFood pf = null;
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs=  null;
       
        
        try {
        	conn = getConnect();
        	
        	String query ="";
        	
        	if(pet.getSpecies() == 0) { // 고양이
        		if (pet.isKidney()) {// 신장병 있음
        			query = "SELECT id, name, price, stock, species, texture, satisfaction, protein, calories "
        					+ "FROM ( "
        					+ "SELECT * "
        					+ "FROM pet_food "
        					+ "WHERE species = 0 AND "  //고양이
        					+ "protein = 0 AND " // 단백질 X
        					+ "texture = ? "
        					+ "ORDER  BY satisfaction DESC ) "
        					+ "WHERE ROWNUM = 1 ";
        		}else {
        			query = "SELECT id, name, price, stock, species, texture, satisfaction, protein, calories "
        					+ "FROM ( "
        					+ "SELECT * "
        					+ "FROM pet_food "
        					+ "WHERE SPECIES = 0 AND " // 고양이
        					+ "protein = 1 AND " // 단백질 O
        					+ "texture = ? "
        					+ "ORDER  BY satisfaction DESC ) "
        					+ "WHERE ROWNUM = 1 ";
        		}
        	}else {// 강아지
				if (pet.isWalktime()) {// 많이 걸음
					query = "SELECT id, name, price, stock, species, texture, satisfaction, protein, calories "
        					+ "FROM ( "
        					+ "SELECT * "
        					+ "FROM pet_food "
        					+ "WHERE species = 1 AND "  // 강아지
        					+ "calories = 1 AND " // 칼로리 높은 것
        					+ "texture = ? "
        					+ "ORDER  BY satisfaction DESC ) "
        					+ "WHERE ROWNUM = 1 ";
				    
        		}else { // 많이 안 걸음
        			query = "SELECT id, name, price, stock, species, texture, satisfaction, protein, calories "
        					+ "FROM ( "
        					+ "SELECT * "
        					+ "FROM pet_food "
        					+ "WHERE species = 1 AND "  // 강아지
        					+ "calories = 0 AND " // 칼로리 낮은 것
        					+ "texture = ? "
        					+ "ORDER  BY satisfaction DESC ) "
        					+ "WHERE ROWNUM = 1 ";
        		}
        	}
//        	System.out.println(query);
        	ps = conn.prepareStatement(query);
        	ps.setInt(1, texture);
        	rs = ps.executeQuery();
        	/* int id, String name, int price, int stock, boolean species, 
        	 * int texture, double satisfaction, boolean protein, boolean calories
        	 */
        	while(rs.next()) {
        		pf = new PetFood(
            			
            			rs.getInt("id"),
            			rs.getString("name"),
            			rs.getInt("price"),
            			rs.getInt("stock"),
            			rs.getBoolean("species"),
            			rs.getInt("texture"),
            			rs.getDouble("satisfaction"),
            			rs.getBoolean("protein"),
            			rs.getBoolean("calories")
            			);
        	}
        	
        	
        	System.out.println(pf);
        	return pf;
        	
        }finally {
        	closeAll(rs, ps, conn);
        }
	}

	@Override
    public PetFood recommPetFood(Pet pet, int budget, int texture) throws SQLException {
        PetFood pf = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs=  null;
       
        
        try {
            conn = dao.getConnect();
            
            String query ="";
            
            if(pet.getSpecies() == 0) { // 고양이
                if (pet.isKidney()) {// 신장병 있음
                    query = "SELECT id, name, species, price, stock, texture, satisfaction, calories, "
                            + "DECODE(protein, '1', '고단백', '0', '저단백') protein "
                            + "FROM ( "
                            + "SELECT * "
                            + "FROM pet_food "
                            + "WHERE protein = 0 AND " // 단백질 X
                            + "texture = ? AND"
                            + "price < ? "
                            + "ORDER BY satisfaction) "
                            + "WHERE ROWNUM = 1";
                }else {
                    query = "SELECT id, name, species, price, stock, texture, satisfaction, calories, "
                            + "DECODE(protein, '1', '고단백', '0', '저단백') protein "
                            + "FROM ( "
                            + "SELECT * "
                            + "FROM pet_food "
                            + "WHERE protein = 1 AND " // 단백질 X
                            + "texture = ? AND "
                            + "price < ? "
                            + "ORDER BY satisfaction) "
                            + "WHERE ROWNUM = 1";
                }
            } else {// 강아지
                if (pet.isWalktime()) {// 많이 걸음
                    query = "SELECT id, name, species, price, stock, texture, satisfaction, calories, "
                            + "DECODE(walktime, '1', '고단백', '0', '저단백') protein "
                            + "FROM ( "
                            + "SELECT * "
                            + "FROM pet_food "
                            + "WHERE walktime = 1 AND " // 단백질 X
                            + "texture = ? AND "
                            + "price < ? "
                            + "ORDER BY satisfaction) "
                            + "WHERE ROWNUM = 1";
                    
                }else { // 많이 안 걸음
                    query = "SELECT id, name, species, price, stock, texture, satisfaction, calories, "
                            + "DECODE(walktime, '1', '고단백', '0', '저단백') protein "
                            + "FROM ( "
                            + "SELECT * "
                            + "FROM pet_food "
                            + "WHERE walktime = 0 AND " // 단백질 X
                            + "texture = ? AND "
                            + "price < ? "
                            + "ORDER BY satisfaction) "
                            + "WHERE ROWNUM = 1";
                }
            }
            
           
            ps = conn.prepareStatement(query);
            ps.setInt(1, texture);
            ps.setInt(2, budget);
            rs = ps.executeQuery();
            /* int id, String name, int price, int stock, boolean species, 
             * int texture, double satisfaction, boolean protein, boolean calories
             */
            rs.next();
            pf = new PetFood(
                    
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getInt("stock"),
                    rs.getBoolean("species"),
                    rs.getInt("texture"),
                    rs.getDouble("satisfaction")
//                    rs.getBoolean("protein"),
//                    rs.getBoolean("calories")
                    );
            
            System.out.println(pf);
            return pf;  
            
        } finally {
            dao.closeAll(rs, ps, conn);
        } 
    }

	@Override
	public void buyPetFood(Customer cust, PetFood petfood, int quantity) throws SQLException, InvalidTransactionException, RecordNotFoundException, DuplicateIdException, ParseException {
        double cbudget = cust.getBudget();
        int cost = petfood.getPrice() * quantity;
        
        if (cbudget < cost) {
            throw new InvalidTransactionException();
        }
    
        cust.setBudget(cust.getBudget() - cbudget);
        petfood.setStock(petfood.getStock() - quantity);
        
//        Cart cart = new Cart( quantity, Date.valueOf("2023-08-06"), cust.getId(), petfood.getId());
        
//        dao.addCart(cart);
        dao.updateCustomer(cust);
        dao.updatePetFood(petfood);
    }

	@Override
	public ArrayList<PetFood> getRanking(int texture) throws SQLException {
		ArrayList<PetFood> pflist = new ArrayList<PetFood>();
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs=  null;
		String query = "select * from pet_food  where texture = ?  order by satisfaction desc";
		
		try {
        	conn = getConnect();
        	ps = conn.prepareStatement(query);
        	
        	rs = ps.executeQuery();
        	while(rs.next()) {
        		//int id, String name, int price, int stock, boolean species, int texture, double satisfaction,
    			//boolean protein, boolean calories
        		pflist.add(new PetFood(
        				rs.getInt("id"),
            			rs.getString("name"),
            			rs.getInt("price"),
            			rs.getInt("stock"),
            			rs.getBoolean("species"),
            			rs.getInt("texture"),
            			rs.getDouble("satisfaction"),
            			rs.getBoolean("protein"),
            			rs.getBoolean("calories"))
        				
        				);
        	}
        	
        	
		}finally {
			closeAll(rs, ps, conn);
		}
		
		
		return pflist;
	}

	@Override
	public Cart getRecentCart(Customer cust) throws SQLException {
		Cart cart = null;
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs=  null;
        try {
        	conn = getConnect();
        	String query   = " select id, cid, pfid, quantity, cart_date from ( select * from cart where cid = ? order by cart_date desc ) where rownum =1";
        	ps = conn.prepareStatement(query);
        	ps.setInt(1, cust.getId());
        	rs = ps.executeQuery();
        	
        	while(rs.next()) { // Result 값이 없다 이렇게 나와서 while 적어 줌
        		// 에러명: ResultSet.next가 호출되지 않았음
        		cart = new Cart(
            			
            			rs.getInt("id"),
            			rs.getInt("quantity"),
            			rs.getDate("cart_date;"),
            			
            			rs.getInt("cid"),
            			rs.getInt("pfid")
            			
        				);
        	}
        	
        	
        }finally {
        	closeAll(rs, ps, conn);
        }
		
		
		return cart;
	}

	@Override
	public ArrayList<Cart> getBestPetFoodMonth() throws SQLException {
		ArrayList<Cart> cartlist = new ArrayList<Cart>();
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs=  null;
        
		try {
			conn = getConnect();
			String query   = ""; // 작성해야함
        	ps = conn.prepareStatement(query);
        	rs = ps.executeQuery();
        	
        	while(rs.next()) { // Result 값이 없다 이렇게 나와서 while 적어 줌
        		// 에러명: ResultSet.next가 호출되지 않았음
        		cartlist.add(new Cart(
            			
            			rs.getInt("id"),
            			rs.getInt("quantity"),
            			rs.getDate("cart_date;"),
            			
            			rs.getInt("cid"),
            			rs.getInt("pfid")
            			));
        	}
        	
			return cartlist;
			
		}finally {
			closeAll(rs, ps, conn);
		}
		
		
	}
	@Override
	public void buyPetFood(int budget, PetFood petfood, int quantity) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	

}