package we.pet.vo;

public class PetFood {

	private int id;
	private String name;
	private int price;
	private int stock;
	private boolean species;
	private int texture;
	private double satisfaction;
	private boolean protein;
	private boolean calories;
	
	
	public PetFood(int id, String name, int price, int stock, boolean species, int texture, double satisfaction,
			boolean protein, boolean calories) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.species = species;
		this.texture = texture;
		this.satisfaction = satisfaction;
		this.protein = protein;
		this.calories = calories;
	}
	
	public PetFood() {}

	public PetFood(int price, int stock, double satisfaction, int id) {
		super();
		this.price = price;
		this.stock = stock;
		this.satisfaction = satisfaction;
		this.id = id;
//		this.name = "good 사료";
//		this.species = false;
//		this.texture = 3;
//		this.protein = true;
//		this.calories = false;
		
	}
	public PetFood(int stock, int id) {
		super();
		this.stock = stock;
		this.id = id;
	}
	public PetFood(double satisfaction, int id) {
		super();
		this.satisfaction = satisfaction;
		this.id = id;
		
	}
	
	// recommend
	public PetFood(int id, String name, int price, int stock, boolean species, int texture, double satisfaction) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.species = species;
		this.texture = texture;
		this.satisfaction = satisfaction;
	}

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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public boolean isSpecies() {
		return species;
	}

	public void setSpecies(boolean species) {
		this.species = species;
	}

	public int getTexture() {
		return texture;
	}

	public void setTexture(int texture) {
		this.texture = texture;
	}

	public double getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(double satisfaction) {
		this.satisfaction = satisfaction;
	}

	public boolean isProtein() {
		return protein;
	}

	public void setProtein(boolean protein) {
		this.protein = protein;
	}

	public boolean isCalories() {
		return calories;
	}

	public void setCalories(boolean calories) {
		this.calories = calories;
	}

	@Override
	public String toString() {
		return "PetFood [id=" + id + ", name=" + name + ", price=" + price + ", stock=" + stock + ", species=" + species
				+ ", texture=" + texture + ", satisfaction=" + satisfaction + ", protein=" + protein + ", calories="
				+ calories + "]";
	}
	
	
	
	
}
