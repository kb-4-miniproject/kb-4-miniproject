package we.pet.vo;

public class Dog extends Pet{

	private boolean walktime;
	

	public Dog(int id, String name, int age, boolean sex, boolean walktime) {
		super(id, name, age, sex);
		this.walktime = walktime;
	}
	public Dog(){}
	
	public boolean isWalktime() {
		return walktime;
	}

	public void setWalktime(boolean walktime) {
		this.walktime = walktime;
	}
	@Override
	public String toString() {
		return "Dog [walktime=" + walktime + "]";
	}
	
	
}
