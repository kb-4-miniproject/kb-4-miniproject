package we.pet.vo;

import java.sql.Date;

public class Cart {

	private int id;
	private int quantity;
	private Date cartDate;
	private int cid;
	private int pfid;
	
	// update cart 위한 생성자
	public Cart(int id, int quantity) {
		super();
		this.id = id;
		this.quantity = quantity;
	}

	public Cart(int id, int quantity, Date cartDate, int cid, int pfid) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.cartDate = cartDate;
		this.cid = cid;
		this.pfid = pfid;
	}
	
	public Cart() {}

	public Cart(int quantity, Date cartDate, int cid, int pfid) {
		this.quantity = quantity;
		this.cartDate = cartDate;
		this.cid = cid;
		this.pfid = pfid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getCartDate() {
		return cartDate;
	}

	public void setCartDate(Date cartDate) {
		this.cartDate = cartDate;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getPid() {
		return pfid;
	}

	public void setPid(int pid) {
		this.pfid = pid;
	}
	
	
	@Override
	public String toString() {
		return "Cart [id=" + id + ", quantity=" + quantity + ", cartDate=" + cartDate + ", cid=" + cid + ", pfid=" + pfid
				+ "]";
	}
	
	}
	
