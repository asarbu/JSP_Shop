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
import framework.User;

public class UserDAOFile implements UserDAO{

	private HashMap<String, User> hashmap = new HashMap<String, User>();
	private String hashFile = "userFile.txt";

	@Override
	public User login(String username, String password) {
		deserialize();
		if(hashmap.containsKey(username)) {
			User u = hashmap.get(username);
			if(u.getPassword().equals(password)) {
				return u;
			}
		}
			
		return null;
	}

	@Override
	public boolean register(User user) {
		if(!hashmap.containsKey(user.getUsername())) {
			hashmap.put(user.getUsername(), user);
			serialize();
			return true;
		} 
		return false;
	}

	public boolean deserialize() {
		  try {
			  File file = new File(hashFile);
			  FileInputStream fis = new FileInputStream(file);
			  ObjectInputStream ois = new ObjectInputStream(fis);
			  hashmap = (HashMap<String, User>) ois.readObject();
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
		  
		  System.out.println("Deserialized HashMap..\n");
		  
		  for(User entry : hashmap.values()) {
			  System.out.println("ID: " + entry.getUsername() + " - " + entry.getPassword());
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
	public User get(String username) {
		return hashmap.get(username);
	}
}
