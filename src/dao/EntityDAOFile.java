package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import framework.Entity;

public class EntityDAOFile implements EntityDAO {
	private HashMap<Integer, Entity> hashmap = new HashMap<Integer, Entity>();
	private String hashFile = "hashFile.txt";
	
	@Override
	public List<Entity> getAll() {
		deserialize();
		return new ArrayList<Entity>(hashmap.values());
	}

	@Override
	public boolean update(Entity e) {
		for(Entity entity : hashmap.values()) {
			if(entity.getId() == e.getId()) {
				entity.setCategory(e.getCategory());
				entity.setName(e.getName());
				entity.setPrice(e.getPrice());
				entity.setQuantity(e.getQuantity());
				if(serialize()) 
					return true;
				return false;
			}			
		}
		return false;
	}

	@Override
	public boolean remove(Entity e) {
		for(Entity entity : hashmap.values()) {
			if(entity.getId() == e.getId()) {
				hashmap.remove(entity.getId());
				if(serialize()) 
					return true;
				return false;
			}			
		}
		return false;
	}
	
	public boolean deserialize() {
		  try
		  {
			  File file = new File(hashFile);
			  FileInputStream fis = new FileInputStream(file);
			  ObjectInputStream ois = new ObjectInputStream(fis);
			  hashmap = (HashMap<Integer, Entity>) ois.readObject();
			  ois.close();
			  fis.close();
		  }catch(IOException ioe)
		  {
		      ioe.printStackTrace();
		      return false;
		  }catch(ClassNotFoundException c) {
		      System.out.println("Class not found");
		      c.printStackTrace();
		      return false;
		  }
		  
		  System.out.println("Deserialized HashMap..");
		  
		  for(Entity entry : hashmap.values()) {
			  System.out.println("ID: " + entry.getId() + " - " + entry.getName());
		  }
	      return true;
	}
	
	public boolean serialize() {
		try {
			File file = new File(hashFile);
            FileOutputStream fos =
               new FileOutputStream(file, false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(hashmap);
            oos.close();
            fos.close();
            System.out.printf("Serialized HashMap data is saved in " + hashFile);
            return true;
		}catch(IOException ioe) {
            ioe.printStackTrace();
            return false;
		}
	}

	@Override
	public boolean add(Entity e) {
		hashmap.put(e.getId(), e);
		if(serialize()) 
			return true;
		return false;
	}

	@Override
	public List<String> getCategories() {
		List<String> categories = new ArrayList<String>();
		for(Entity e : hashmap.values()) {
			if(!categories.contains(e.getCategory())) {
				categories.add(e.getCategory());
			}
		}
		return categories;
	}

	@Override
	public List<Entity> getAllForCategory(String category) {
		List<Entity> entities = new ArrayList<Entity>();
		for(Entity e : hashmap.values()) {
			if(category.equals(e.getCategory())) {
				entities.add(e);
			}
		}
		return entities;
	}
}
