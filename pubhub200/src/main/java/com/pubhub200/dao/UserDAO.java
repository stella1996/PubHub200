package com.pubhub200.dao;


import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pubhub200.model.User;


@Repository
public class UserDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<User> findAll() {
		String sql = "select id,name,email from users";

		List<User> userList = jdbcTemplate.query(sql, (rs, rowno) -> {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			return user;
		});
		LOGGER.info("List of users retrieval success");
		return userList;
	}

	public User findOne(Integer id) {
		LOGGER.debug("findone - ID{}", new Object[] { id });
		LOGGER.info("Entering findOne()");
		User userObj = null;
		try {
		String sql = "select id,name,email from users  where id = ?";
		Object[] params = new Object[] { id };
		 userObj = jdbcTemplate.queryForObject(sql, params, (rs, rowno) -> {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			return user;
		});
		LOGGER.info("user retrieval success");
		}catch (EmptyResultDataAccessException e){
			LOGGER.debug("User id not found - ID{}", new Object[] { id });
		}
		return userObj;
	}

	public void save(User user) {
		try {
		String sql = "insert into users ( name, email, password)  values ( ?, ? , ? )";
		Object[] params = new Object[] { user.getName(), user.getEmail(), user.getPassword() };
		int rows = jdbcTemplate.update(sql, params);
		LOGGER.info("No of rows inserted - {}", rows);;
	}catch ( DuplicateKeyException e){
		LOGGER.error("unable to save user");
		throw new DataAccessResourceFailureException("unable to save user", e);
	}
	}

	public void update(User user) {

		String sql = "update users set name= ?, email = ? , password = ?  where id = ?";
		Object[] params = new Object[] { user.getName(), user.getEmail(), user.getPassword(),
				user.getId() };
		int rows = jdbcTemplate.update(sql, params);
		LOGGER.info("No of rows updated - {}", rows);
	}

	public void delete(Integer id) {

		String sql = "delete from users where id = ?";
		Object[] params = new Object[] { id };
		int rows = jdbcTemplate.update(sql, params);
		LOGGER.info("No of rows deleted - {}", rows);
	}

	

	public boolean login(String email, String password) throws SQLException {

		Boolean Status = false;
		try {
			String query = "select email,password from  users where email =? and password =?";
			Object[] args = new Object[] { email, password };
			User userObj = jdbcTemplate.queryForObject(query, args, (rs, rowno) -> {
				User user = new User();
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
				}
				return user;

			});
			Status = true;
			LOGGER.info("user retrieval success");
		} catch (Exception e) {
			Status = false;
			LOGGER.debug("User id not found - ID{}");

		}
		return Status;

	}

}
