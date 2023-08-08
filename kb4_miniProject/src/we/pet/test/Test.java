package we.pet.test;


import oracle.jdbc.proxy.annotation.GetCreator;
import we.pet.dao.CustDAO;
import we.pet.dao.impl.CustDAOImpl;
import we.pet.dao.impl.CustServiceImpl;
import we.pet.vo.Cart;
import we.pet.vo.Customer;
import we.pet.vo.Pet;
import we.pet.vo.PetFood;

public class Test {

	public static void main(String[] args) throws Exception {
		//CustDAO dao = new CustDAO("127.0.0.1");
		/*******************************************
		 ************* basic CRUD ******************
		 *******************************************/
		CustDAOImpl dao = CustDAOImpl.getInstance();
		/////////////////////// add ///////////////////////
//		dao.addCustomer(new Customer("Tomas", "567-47-0915", "Texas" ,"010-5555-5555" , 50000.00));		
		
		/////////////////////// get ///////////////////////
//		dao.getAllCustomers();
//		dao.getAllPets();
//		dao.getAllPetFoods();
//		dao.getAllOrders();
		
		dao.getCustomer(1);
		dao.getOrder(2);
		dao.getPet(2);
		dao.getPetFood(10);
		
		/////////////////// UPDATE //////////////////////////
		dao.updateCustomer(new Customer("메리", "정릉", "010-7296-2003", 50000.00, 5));
		dao.updatePetFood(new PetFood(90000, 15, 3.5, 4));
		dao.updateSatisfaction(new PetFood(3.5, 3));
		dao.updateStock(new PetFood(20, 4));
		dao.updateCart(new Cart(10, 5));
		
		/*******************************************
		 ************* advanced func ******************
		 *******************************************/
		
		CustServiceImpl cs = CustServiceImpl.getInstance();
		//Pet(int id, String name, int age, boolean sex, boolean walktime, boolean kidney)
		// 0은 고양이 1은 강아지
		////////////////////// 사료 추천 ///////////////////// 
		Pet p = dao.getPet(5);
		cs.recommPetFoodByBudget(p, 50000);
		cs.recommPetFoodByTexture(p, 4);
//		cs.recommPetFood(p, 4, 50000);
		
		///////////////////// 사료 구매 /////////////////////
		Customer cust = dao.getCustomer(8);
		PetFood pf = dao.getPetFood(7);
		cs.buyPetFood(cust, pf, 2);
		

	}

}
