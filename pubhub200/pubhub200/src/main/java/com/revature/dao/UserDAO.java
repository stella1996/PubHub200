package com.revature.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.revature.model.User;

@Repository
public class UserDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<User> findAll() {
		String sql = "select id,name,email,active from user_account";

		List<User> userList = jdbcTemplate.query(sql, (rs, rowno) -> {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setActive(rs.getBoolean("active"));
			return user;
		});
		return userList;
	}
	
	public User findOne(Integer id) {
		String sql = "select id,name,email,active from user_account where id = ?";
		Object[] params = new Object[]{id};
		User userList = jdbcTemplate.queryForObject(sql, params , (rs, rowno) -> {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setActive(rs.getBoolean("active"));
			return user;
		});
		return userList;
	}

	public void save(User user) {

		String sql = "insert into user_account ( name, email, password)  values ( ?, ? , ? )";
		Object[] params = new Object[] { user.getName(), user.getEmail(), user.getPassword() };
		int rows = jdbcTemplate.update(sql, params);
		System.out.println("No of rows inserted:" + rows);
	}
	
	public void update(User user) {

		String sql = "update user_account set name= ?, email = ? , password = ?  ,active = ?  where id = ?";
		Object[] params = new Object[] { user.getName(), user.getEmail(), user.getPassword(), user.isActive(),user.getId() };
		int rows = jdbcTemplate.update(sql, params);
		System.out.println("No of rows updated:" + rows);
	}

	public void delete(Integer id) {

		String sql = "delete from user_account where id = ?";
		Object[] params = new Object[] { id };
		int rows = jdbcTemplate.update(sql, params);
		System.out.println("No of rows deleted:" + rows);
	}


	
	public User validate(String email, String password) {
		String sql = "select email,password from user_account where email=? and password=?";
		Object[] params = new Object[]{email,password};
		User userList = jdbcTemplate.queryForObject(sql, params , (rs, rowno) -> {
			User user = new User();
			user.setPassword(rs.getString("password"));
			user.setEmail(rs.getString("email"));
			
			if(user.equals("email")&& user.equals("password"))
					
					{
				return user;
					}
			else
			{
				return null;
			}
			});
		return userList;
	}

}
