package com.custApp.model.persistance;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory factory;

	public Session getSession() {
		return factory.getCurrentSession();
	}

	@Override
	public User getUser(String email, String password) {
		@SuppressWarnings("unchecked")
		List<User> user = getSession().createQuery("select u from User u where u.email=:email and u.password=:password")
				.setString("email", email).setString("password", password).list();
		if (user.size() > 0)
			return user.get(0);
		else
			throw new UserNotFoundException("user with this email" + " " + email + " is not there");
		
	}

	@Override
	public void addUser(User user) {
		getSession().save(user);
	}

	@Override
	public List<User> getAllUser() {
		
		return getSession().createQuery("from User").list();
	}

}
