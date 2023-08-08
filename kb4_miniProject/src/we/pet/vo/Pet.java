//package we.pet.vo;
//
//public class Pet {
//
//	protected int id;
//	protected String name;
//	protected int age;
//	protected boolean sex;
//
//	
//	public Pet(int id, String name, int age, boolean sex) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.age = age;
//		this.sex = sex;
//	
//	}
//
//	
//	
//	public Pet() {}
//	
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public int getAge() {
//		return age;
//	}
//	public void setAge(int age) {
//		this.age = age;
//	}
//	public boolean isSex() {
//		return sex;
//	}
//	public void setSex(boolean sex) {
//		this.sex = sex;
//	}
//
//	@Override
//	public String toString() {
//		return "Pet [id=" + id + ", name=" + name + ", age=" + age + ", sex=" + sex + "]";
//	}
//	
//	
//	
//	
//}

package we.pet.vo;

public class Pet {

	protected int id;
	protected String name;
	protected int age;
	protected boolean sex;
	protected boolean walktime;
	protected boolean kidney;
	protected int species;
	
	// 상속 위한 생성자 
	public Pet(int id, String name, int age, boolean sex) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.sex = sex;
	}
	
	public Pet(int id, String name, int age, boolean sex, boolean walktime, boolean kidney) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.walktime = walktime;
		this.kidney = kidney;
	}
	
	public Pet(int id, String name, int age, boolean sex, boolean walktime, boolean kidney, int species) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.walktime = walktime;
		this.kidney = kidney;
		this.species = species; // 0은 고양이
	}
	public int getSpecies() {
		return this.species;
	}
	public void setSpecies(int species) {
		this.species = species;
	}
	
	public Pet() {}
	
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public boolean isSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	public boolean isWalktime() {
		return walktime;
	}
	public void setWalktime(boolean walktime) {
		this.walktime = walktime;
	}
	public boolean isKidney() {
		return kidney;
	}
	public void setKidney(boolean kidney) {
		this.kidney = kidney;
	}
	@Override
	public String toString() {
		return "Pet [id=" + id + ", name=" + name + ", age=" + age + ", sex=" + sex + ", walktime=" + walktime
				+ ", kidney=" + kidney + ", species=" + species +"]";
	}
	
	
	
	
}
