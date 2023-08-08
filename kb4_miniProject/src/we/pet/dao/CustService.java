package we.pet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import we.pet.exception.InvalidTransactionException;
import we.pet.exception.RecordNotFoundException;
import we.pet.vo.Cart;
import we.pet.vo.Customer;
import we.pet.vo.Pet;
import we.pet.vo.PetFood;

public interface CustService {
	// 공통 로직 
	void closeAll(PreparedStatement ps, Connection conn) throws SQLException;
	void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException;
	Connection getConnect() throws SQLException;
	
///// advanced service ////
	// recommend 
	PetFood recommPetFoodByBudget(Pet pet, int budget) throws SQLException;
	PetFood recommPetFoodByTexture(Pet pet, int texture) throws SQLException;
	PetFood recommPetFood(Pet pet, int budget, int texture) throws SQLException; 
	
	// buy food
	void buyPetFood(int budget, PetFood petfood, int quantity) throws SQLException;

	// Ranking
	ArrayList<PetFood> getRanking(int texture) throws SQLException;
	
  // log
	Cart getRecentCart(Customer cust) throws SQLException;
	ArrayList<Cart> getBestPetFoodMonth() throws SQLException;
	void buyPetFood(Customer cust, PetFood petfood, int quantity) throws SQLException, InvalidTransactionException,
			RecordNotFoundException, we.pet.exception.DuplicateIdException, java.text.ParseException;
	
}