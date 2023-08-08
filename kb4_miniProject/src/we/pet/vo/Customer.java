package we.pet.vo;

import java.util.ArrayList;

public class Customer {

	private int id;
	private String name;
	private String ssn;
	private String address;
	private String phone;
	private double budget;
	
	private ArrayList<Pet> petInfo;
	
	// add customer 위한 생성자
	public Customer(String name, String ssn, String address, String phone, double budget) {
		super();
		this.name = name;
		this.ssn = ssn;
		this.address = address;
		this.phone = phone;
		this.budget = budget;
	}
	
	// update customer 위한 생성자
	public Customer(String name, String address, String phone, double budget, int id) {
		super();
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.budget = budget;
		this.id = id;
	}

	public Customer(int id, String name, String ssn, String address, String phone, double budget,
			ArrayList<Pet> petInfo) {
		super();
		this.id = id;
		this.name = name;
		this.ssn = ssn;
		this.address = address;
		this.phone = phone;
		this.budget = budget;
		this.petInfo = petInfo;
	}
	public Customer(int id, String name, String ssn, String address, String phone, double budget
			) {
		super();
		this.id = id;
		this.name = name;
		this.ssn = ssn;
		this.address = address;
		this.phone = phone;
		this.budget = budget;
		
	}

	public Customer() {}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public ArrayList<Pet> getPetInfo() {
		return petInfo;
	}

	public void setPetInfo(ArrayList<Pet> petInfo) {
		this.petInfo = petInfo;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", ssn=" + ssn + ", address=" + address + ", phone=" + phone
				+ ", budget=" + budget + ", petInfo=" + petInfo + "]";
	}
	
	
	
}
