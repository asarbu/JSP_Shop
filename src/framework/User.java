package framework;

import java.util.List;

import dao.EntityDAO;
import dao.EntityDAOSQL;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String username;
	private String password;
	private String name;
	private String address;
	private List<Entity> shopCart = new ArrayList<Entity>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public boolean addToCart(Entity e) {
		EntityDAO edao = new EntityDAOSQL();
		List<Entity> all = edao.getAll();
		System.out.println("To search: " + e);
		for(Entity temp : all) {
			System.out.println("Against: " + temp);
			if(temp.getId() == e.getId() && temp.getQuantity() >= e.getQuantity()) {
				//e contains the quantity
				//e.getQuantity()
				System.out.println("Product found! Adding to cart: " + e.toString());
				this.shopCart.add(e);
				return true;
			}
		}
		System.out.println("Product not found: " + e.toString());
		
		return false;
	}
	public void removeFromCart(Entity e) {
		for(Entity entity : this.shopCart) {
			if(entity.getId() == e.getId()) {
				this.shopCart.remove(entity);
			}
		}
	}
	public List<Entity> getCart() {
		return shopCart;
		
	}
}
