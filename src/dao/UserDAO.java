package dao;

import java.util.List;
import framework.*;

public interface UserDAO {
	public User get(String username);
	public boolean update(User user);
	public boolean checkout(User user);
	public User login(String username, String password);
	public boolean register(User user);
}
