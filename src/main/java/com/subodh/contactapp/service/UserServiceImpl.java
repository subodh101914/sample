package com.subodh.contactapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.subodh.contactapp.dao.BaseDAO;
import com.subodh.contactapp.dao.UserDAO;
import com.subodh.contactapp.domain.User;
import com.subodh.contactapp.exceptions.UserBlockedException;
import com.subodh.contactapp.rm.UserRowMapper;

@Service
public class UserServiceImpl extends BaseDAO implements UserService{
	
	@Autowired
	private UserDAO userDAO;
		
	@Override
	public void register(User u) {
		userDAO.save(u);
	}

	@Override
	public User login(String loginName, String password) throws UserBlockedException {
		String sql = "SELECT userId, name, phone, email, address, role, loginStatus, loginName"
                + " FROM user WHERE loginName=:ln AND password=:pw";
        Map<String,Object> m = new HashMap<>();
        m.put("ln", loginName);
        m.put("pw", password);
        try {
            User u = getNamedParameterJdbcTemplate().queryForObject(sql, m, new UserRowMapper());
            if (u.getLoginStatus().equals(UserService.LOGIN_STATUS_BLOCKED)) {
                throw new UserBlockedException("Your accout has been blocked. Contact to admin.");
            } else {
                return u;
            }
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
	}

	@Override
	public List<User> getUserList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changeLoginStatus(Integer userId, Integer loginStatus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean isUsernameExist(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
