package we.pet.vo;

public class Cat extends Pet {

	private boolean kidney;

	
	public Cat(int id, String name, int age, boolean sex, boolean kidney) {
		super(id, name, age, sex);
		this.kidney = kidney;
	}

	public Cat() {}
	
	public boolean isKidney() {
		return kidney;
	}

	public void setKidney(boolean kidney) {
		this.kidney = kidney;
	}

	
	@Override
	public String toString() {
		return "Pet [id=" 
					+ id +
					", name=" 
					+ name + 
					", age=" 
					+ age + 
					", sex="
					+ sex + ", kidney=" + kidney +
					"]" ;
	}
	
	

}
