package dao;

import java.util.List;
import framework.Entity;

public interface EntityDAO {
	public List<Entity> getAll();
	public List<String> getCategories();
	public List<Entity> getAllForCategory(String category);
	public boolean add(Entity e);
	public boolean update(Entity e);
	public boolean remove(Entity e);
}
