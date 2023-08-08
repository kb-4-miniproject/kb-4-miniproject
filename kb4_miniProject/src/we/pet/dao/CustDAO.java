package we.pet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import we.pet.exception.RecordNotFoundException;
import we.pet.vo.Customer;
import we.pet.vo.Pet;
import we.pet.vo.PetFood;

public interface CustDAO {
	Connection getConnect() throws SQLException;
	void closeAll(PreparedStatement ps, Connection conn)throws SQLException;
	void closeAll(ResultSet rs, PreparedStatement ps, Connection conn)throws SQLException;
	
	// ADD
	void addCustomer(Customer cust) throws SQLException, we.pet.exception.DuplicateIdException;
	
	// GET
	

	// UPDATE
	void updateCustomer(Customer cust) throws SQLException, RecordNotFoundException;
	void updatePetFood(PetFood p) throws SQLException;
	void updateStock(PetFood p) throws SQLException;
	void updateSatisfaction(PetFood p) throws SQLException;
	
}
