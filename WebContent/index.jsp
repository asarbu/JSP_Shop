<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="framework.*, dao.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index</title>
</head>
<body>
	<h1>
	Welcome 
	<% 
		User u = new User();
		u.setUsername("Sebi");
		u.setPassword("password");
		
		UserDAO user_dao = new UserDAOFile();
		user_dao.register(u);
		User logged_in_user = user_dao.login("Sebi", "password");
	%>
	<%=logged_in_user.getUsername() %>
	<br>
				
	<% 
		EntityDAO entity_dao = new EntityDAOFile();

		Entity e = new Entity(); 
		e.setId(1);
		e.setName("E1");
		e.setDescription("Description");
		e.setCategory("Category");
		entity_dao.add(e);
		
		e = new Entity(); 
		e.setId(2);
		e.setName("E2");
		e.setDescription("Description");
		e.setCategory("Category");
		entity_dao.add(e);
		
		e = new Entity(); 
		e.setId(3);
		e.setName("E3");
		e.setDescription("Description");
		e.setCategory("Category");
		entity_dao.add(e);
		e.setName("E4");
		entity_dao.update(e);
		
		for(Entity entity: entity_dao.getAll()) {
	%>
			<h2><%=entity.getName()%></h2> <br>
	<% 
		} 
	%>
	</h1>
</body>
</html>