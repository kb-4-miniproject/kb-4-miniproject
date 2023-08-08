package we.pet.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import config.ServerInfo;
import we.pet.dao.CustDAO;
import we.pet.exception.DuplicateIdException;
import we.pet.exception.RecordNotFoundException;
import we.pet.vo.*;

public class CustDAOImpl implements CustDAO{
	// 싱글톤 패턴 구현 
		private static CustDAOImpl dao = new CustDAOImpl();
		private CustDAOImpl(){
			System.out.println("CustDAOImpl Creating...Using Singletone");
		}
		public static CustDAOImpl getInstance() {
			return dao;
		}
	
	public CustDAOImpl(String serverIp) throws ClassNotFoundException {
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
	
	
	//ssn 여부 확인 
	private boolean isExist(int id, Connection conn) throws SQLException{
		String query = "SELECT id FROM customer WHERE id = ?";
		System.out.println(query + id);
		PreparedStatement ps = conn.prepareStatement(query);
		ps = conn.prepareStatement(query);
    	ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		return rs.next();
	}
	
//	// id 여부 확인
	private boolean idExist(int id, String table, Connection conn) throws SQLException {
	        String query = "SELECT id FROM " + table + " WHERE id = ?";;
	        PreparedStatement ps = conn.prepareStatement(query);
//	        ps = conn.prepareStatement(query);
	        ps.setInt(1, id);
	        ResultSet rs = ps.executeQuery();
	        System.out.println(1);
	        return rs.next();
	    }
		
	
	// getAll ~~ : 작동 완료
	public ArrayList<Customer> getAllCustomers() throws SQLException{
		
		ArrayList<Customer> custlist= new ArrayList<Customer>();
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs=  null;
        Customer cust = null;//new Customer();
        
        try {
        	conn = getConnect();
        	String query = "SELECT * FROM customer";
        	ps = conn.prepareStatement(query);
        	
        	rs = ps.executeQuery();
        	
        	while(rs.next()) {
        		
//        		cust = new Customer(
//        				rs.getInt("id"),
//        				rs.getString("name"),
//        				rs.getString("ssn"),
//            			rs.getString("address"),
//            			rs.getString("phone"),
//            			rs.getDouble("budget"),
//            			getAllPet(rs.getInt("id")));
//        		
//        		System.out.println(cust.toString());
//        		
//        		custlist.add(cust);
        		
        		custlist.add(new Customer(
        				rs.getInt("id"),
        				rs.getString("name"),
        				rs.getString("ssn"),
            			rs.getString("address"),
            			rs.getString("phone"),
            			rs.getDouble("budget"),
            			getAllPet(rs.getInt("id")))
        				
        				);
        		
        		
        		
        	}
        	
        	System.out.println(custlist.toString());
        	return custlist;
        	
        }finally {
        	closeAll(rs, ps, conn);
        }
	}
	public ArrayList<Pet> getAllPets() throws SQLException{
		ArrayList<Pet> petList= new ArrayList<Pet>();
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs=  null;
        try {
        	conn = getConnect();
        	String query = "SELECT id, name, age, sex, walktime, kidney "
        			+ "FROM pet";
        	ps = conn.prepareStatement(query);
        	
        	rs = ps.executeQuery();
        	
        	while(rs.next()) {
        		System.out.println(rs.getString("id")+'\t'+rs.getString("name"));
        		petList.add(new Pet(
        				rs.getInt("id"),
        				rs.getString("name"),
        				rs.getInt("age"),
            			rs.getBoolean("sex"),
            			rs.getBoolean("walktime"),
            			rs.getBoolean("kidney")
            			
        				)
        				
        				);
        	}
        	
        	return petList;
        	
        }finally {
        	closeAll(rs, ps, conn);
        }
		
	}
	public ArrayList<PetFood> getAllPetFoods() throws SQLException{
		ArrayList<PetFood> PetFoodList= new ArrayList<PetFood>();
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs=  null;
        
        
        try {
        	conn = getConnect();
        	String query = "SELECT * FROM pet_food";
        	ps = conn.prepareStatement(query);
        	
        	rs = ps.executeQuery();
        	
        	while(rs.next()) {
        		System.out.println();
        		System.out.println(rs.getString("id")+'\t'+rs.getString("name"));
        		
        		
        		PetFoodList.add( new PetFood(
                				rs.getInt("id"),
                				rs.getString("name"),
                				rs.getInt("price"),
                				rs.getInt("stock"),
                				rs.getBoolean("species"),
                				rs.getInt("texture"),
                				rs.getDouble("satisfaction"),
                				rs.getBoolean("protein"),
                				rs.getBoolean("calories")
                				));
        	}
        	
        	return PetFoodList;
        	
        }finally {
        	closeAll(rs, ps, conn);
        }
	}
	public ArrayList<Cart> getAllOrders() throws SQLException{
		ArrayList<Cart> orderList= new ArrayList<Cart>();
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs=  null;
        
        
        try {
        	conn = getConnect();
        	String query = "SELECT * FROM cart";
        	ps = conn.prepareStatement(query);
        	
        	rs = ps.executeQuery();
        	
        	while(rs.next()) {
        		System.out.println(rs.getString("id")+'\t'+rs.getString("cid"));
        		orderList.add(
        				new Cart(
        				rs.getInt("id"),
        				rs.getInt("quantity"),
        				rs.getDate("cart_date"),
        				rs.getInt("cid"),
        				rs.getInt("pfid")
        				)
        			);
	
        	}
        	
        	return orderList;
        	
        }finally {
        	closeAll(rs, ps, conn);
        }
	}

	
	//getAllPortfolio 랑 같은 것 --> getAllCustomers에서 사용됨
	// cid에 해당하는 pet의 정보를 가져온다
	private ArrayList<Pet> getAllPet(int cid)throws SQLException{
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs=  null;
        ArrayList<Pet> list = new ArrayList<Pet>();
        try {
        	conn = getConnect();
        	String query = "SELECT id, name, age, sex, walktime, kidney FROM pet WHERE cid = ?";
        	ps = conn.prepareStatement(query);
        	ps.setInt(1, cid);
        	rs = ps.executeQuery();
        	
        	while(rs.next()) {
        		//int id, String name, int age, boolean sex, boolean walktime, boolean kidney
        		list.add(new Pet(
        				rs.getInt("id"),
        				rs.getString("name"),
        				rs.getInt("age"),
        				rs.getBoolean("sex"),
        				rs.getBoolean("walktime"),
        				rs.getBoolean("kidney")));
        	}
        	
        	
        }finally {
        	closeAll(rs, ps, conn);
        }
        
		return list;
	}

	// add 
	@Override
	public void addCustomer(Customer cust) throws SQLException, DuplicateIdException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			if(!idExist(cust.getId(),"customer",conn)) {
			String query = "INSERT INTO customer(id, name, ssn, address, phone, budget) VALUES(cust_seq.nextVal,?,?,?,?,?)";
			ps = conn.prepareStatement(query);
			
			ps.setString(1, cust.getName());
			ps.setString(2, cust.getSsn());
			ps.setString(3, cust.getAddress());
			ps.setString(4, cust.getPhone());
			ps.setDouble(5, cust.getBudget());
			System.out.println(ps.executeUpdate()+"님이 등록되었습니다.");
			
		}else {
			throw new DuplicateIdException();
		}
		}finally{
			closeAll(ps,conn);
		}
		
	}
	
	public void addCart(Cart cart) throws SQLException, DuplicateIdException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnect();
            

            String query = "INSERT INTO cart(id, quantity, cartDate, cid, pfid) VALUES(cart_seq.nextVal,?,?,?,?)"
                    + "WHERE id=?";

            ps = conn.prepareStatement(query);
            System.out.println(1);
            ps.setInt(1, cart.getQuantity());
            ps.setDate(2, cart.getCartDate());
            ps.setDouble(3, cart.getCid());
            ps.setInt(4, cart.getPid());
            int result = ps.executeUpdate();
            System.out.println(result + " 개 상품 업데이트 성공!");
            
        } finally {
            closeAll(ps, conn);
        }

    }

	
	// get~	
	public Customer getCustomer(int id)throws RecordNotFoundException, SQLException {
		
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs=  null;
        Customer cust = null;
        
        try {
        	conn = getConnect();
        	String query = "SELECT name, ssn, address, phone, budget FROM customer where id = ?";
        	ps = conn.prepareStatement(query);
        	ps.setInt(1, id);
        	rs = ps.executeQuery();
        	while(rs.next()) {
        		
        		cust = new Customer(
        				id,
        				rs.getString("name"),
        				rs.getString("ssn"),
            			rs.getString("address"),
            			rs.getString("phone"),
            			rs.getDouble("budget"),
            			getAllPet(id));
        		
        		//System.out.println(cust.toString());
        	}
        	
        	if(cust == null) {
        		throw new RecordNotFoundException();
        	}
        	else {
        		System.out.println(cust.toString());
            	return cust;
        	}  	
        }finally {
        	closeAll(rs, ps, conn);
        }
	}

	public Pet getPet(int id) throws RecordNotFoundException, SQLException{
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs=  null;
        Pet pet = null;
        
        try {
        	conn = getConnect();
        	String query = "SELECT id, name, age, sex, walktime, kidney FROM pet WHERE id = ?";
        	//String query = "SELECT name, ssn, address, phone, budget FROM customer where id = ?";
        	ps = conn.prepareStatement(query);
        	ps.setInt(1, id);
        	rs = ps.executeQuery();
        	
        	while(rs.next()) {
        		//System.out.println(rs.getString("name"));
        		pet = new Pet(
    				rs.getInt("id"),
    				rs.getString("name"),
    				rs.getInt("age"),
    				rs.getBoolean("sex"),
    				rs.getBoolean("walktime"),
    				rs.getBoolean("kidney")
    				);
        				
        				
        		
        		//System.out.println(pet.toString());
        	}
        	
        	if(pet == null) {
        		throw new RecordNotFoundException();
        	}
        	else {
        		System.out.println(pet.toString());
            	return pet;
        	}
        }finally {
        	closeAll(rs, ps, conn);
        }
		
	}
	
	public PetFood getPetFood(int id) throws RecordNotFoundException, SQLException{
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs=  null;
        PetFood pf = null;
        
        try {
        	conn = getConnect();
        	
        	
        	String query = "SELECT "
        			+ "name,price, stock, species, texture, satisfaction, protein, calories "
        			+ "FROM pet_food "
        			+ "WHERE id = ?";
        	ps = conn.prepareStatement(query);
        	ps.setInt(1, id);
        	rs = ps.executeQuery();
        	
        	while(rs.next()) {
        		//System.out.println(rs.getString("name"));
        		pf = new PetFood(
        				id,
        				rs.getString("name"),
        				rs.getInt("price"),
        				rs.getInt("stock"),
        				rs.getBoolean("species"),
        				rs.getInt("texture"),
        				rs.getDouble("satisfaction"),
        				rs.getBoolean("protein"),
        				rs.getBoolean("calories")
        				);
        		
        		//System.out.println(pf.toString());
        	}
        	
        	if(pf == null) {
        		throw new RecordNotFoundException();
        	}
        	else {
        		System.out.println(pf);
            	return pf;
        	}
        	
        	
        }finally {
        	closeAll(rs, ps, conn);
        }
		
	}
	
	public Cart getOrder(int id) throws RecordNotFoundException, SQLException{
		// 무슨 id?
		// 왜 return 형이 pet?
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs=  null;
        Cart cart = null;
        
        try {
        	conn = getConnect();
        	
        	
        	String query = "SELECT cid, pfid, quantity, cart_date FROM cart where id = ?";
        	ps = conn.prepareStatement(query);
        	ps.setInt(1, id);
        	rs = ps.executeQuery();
        	
        	while(rs.next()) {
        		//System.out.println(rs.getString("name"));
        		cart = new Cart(
        				id,
        				rs.getInt("quantity"),
        				rs.getDate("cart_date"),
        				rs.getInt("cid"),
        				rs.getInt("pfid")
        				);
//        		
//        		//System.out.println(cart.toString());
        	}
        	if(cart == null) {
        		throw new RecordNotFoundException();
        	}
        	else {
        		System.out.println(cart);
            	return cart;
        	}
        	
        	
        	//return cart;
        	
        }finally {
        	closeAll(rs, ps, conn);
        }
		
	}

	
	// UPDATE 
	public void updateCustomer(Customer cust) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			String query = "UPDATE customer SET name =? , address =?, phone=?, budget=? WHERE id=? ";
			ps = conn.prepareStatement(query);
			ps.setString(1, cust.getName());
			ps.setString(2, cust.getAddress());
			ps.setString(3, cust.getPhone());
			ps.setDouble(4, cust.getBudget());
			ps.setInt(5, cust.getId());
			
			System.out.println(ps.executeUpdate() + "님이 업데이트 되었습니다.");
			
		}finally {
			closeAll(ps, conn);
		}
	}
		
	@Override
	public void updatePetFood(PetFood p) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			System.out.println(p);
			if (idExist(p.getId(), "pet_food", conn)) {
				
				String query = "UPDATE pet_food SET price=?, stock=?, satisfaction=? WHERE id=?";
				System.out.println(1);

				ps = conn.prepareStatement(query);
				System.out.println(1);
				ps.setInt(1, p.getPrice());
				ps.setInt(2, p.getStock());
				ps.setDouble(3, p.getSatisfaction());
				ps.setInt(4, p.getId());
				int result = ps.executeUpdate();
				System.out.println(result + " 개 상품 업데이트 성공!");
			} else {
				if (!idExist(p.getId(), "pet_food", conn)) new RecordNotFoundException();
				System.out.println("해당 상품을 찾을 수 없습니다!");
				}
			} finally {
			closeAll(ps, conn);
		}
	}

	@Override
	public void updateSatisfaction(PetFood p) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			if (idExist(p.getId(), "pet_food", conn)) {

				String query = "UPDATE pet_food SET stock=? WHERE id=?";

				ps = conn.prepareStatement(query);
				ps.setDouble(1, p.getSatisfaction());
				ps.setInt(2, p.getId());
				int result = ps.executeUpdate();
				System.out.println(result + " 개 상품 업데이트 성공!");
			} else {
				if (idExist(p.getId(), "pet_food", conn)) new RecordNotFoundException();
				System.out.println("해당 상품을 찾을 수 없습니다!");
				}
			} finally {
			closeAll(ps, conn);
		}
		
	}

	@Override
	public void updateStock(PetFood p) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			if (idExist(p.getId(), "pet_food", conn)) {
				
				String query = "UPDATE pet_food SET stock=? WHERE id=?";

				ps = conn.prepareStatement(query);
				ps.setInt(1, p.getStock());
				ps.setInt(2, p.getId());
				int result = ps.executeUpdate();
				System.out.println(result + " 개 상품 업데이트 성공!");
			} else {
				if (idExist(p.getId(), "pet_food", conn)) new RecordNotFoundException();
				System.out.println("해당 상품을 찾을 수 없습니다!");
				}
			} finally {
			closeAll(ps, conn);
		}
		
	}
	
	public void updateCart(Cart cart) throws SQLException , RecordNotFoundException{
        // 무슨 id?
        // 왜 return 형이 pet?
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnect();

            String query = "UPDATE cart quantity = ? where id = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, cart.getId());
            ps.setInt(2, cart.getQuantity());
            
            if (cart == null) {
                throw new RecordNotFoundException();
            } 

        } finally {
            closeAll(ps, conn);
        }
    }

}

















