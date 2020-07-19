package com.subodh.contactapp.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.subodh.contactapp.domain.User;
import com.subodh.contactapp.rm.UserRowMapper;

@Repository
public class UserDAOImpl extends BaseDAO implements UserDAO {
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setDataSource3(DataSource dataSource) {
	    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

    public void save(User u) {
        String sql = "INSERT INTO user(name, phone, email, address, loginName, password, role, loginStatus)"
                + " VALUES(:name, :phone, :email, :address, :loginName, :password, :role, :loginStatus)";
        Map<String, Object> m = new HashMap<>();
        m.put("name", u.getName());
        m.put("phone", u.getPhone());
        m.put("email", u.getEmail());
        m.put("address", u.getAddress());
        m.put("loginName", u.getLoginname());
        m.put("password", u.getPassword());
        m.put("role", u.getRole());
        m.put("loginStatus", u.getLoginStatus());

        KeyHolder kh = new GeneratedKeyHolder();
        SqlParameterSource ps = new MapSqlParameterSource(m);
        namedParameterJdbcTemplate.update(sql, ps, kh);
        Integer userId = kh.getKey().intValue();
        u.setUserId(userId);
    }
    public void update(User u) {
        String sql = "UPDATE user "
                + " SET name=:name,"
                + " phone=:phone, "
                + " email=:email,"
                + " address=:address,"
                + " role=:role,"
                + " loginStatus=:loginStatus "
                + " WHERE userId=:userId";
        HashMap<Object, Object> m = new HashMap<>();
        m.put("name", u.getName());
        m.put("phone", u.getPhone());
        m.put("email", u.getEmail());
        m.put("address", u.getAddress());       
        m.put("role", u.getRole());
        m.put("loginStatus", u.getLoginStatus());
        m.put("userId", u.getUserId());
        namedParameterJdbcTemplate.update(sql, (SqlParameterSource) m);
    }

    @Override
    public void delete(User u) {
        this.delete(u.getUserId());
    }
    @Override
    public void delete(Integer userId) {
        String sql="DELETE FROM user WHERE userId=?";
        getJdbcTemplate().update(sql, userId);
    }

    @Override
    public User findById(Integer userId) {
        String sql = "SELECT userId, name, phone, email, address, loginName, role, loginStatus"
                + " FROM user WHERE userId=?";
        User u = getJdbcTemplate().queryForObject(sql, new UserRowMapper(),userId);
        return u;
    }

    @Override
    public List<User> findAll() {
           String sql = "SELECT userId, name, phone, email, address, loginName, role, loginStatus"
                + " FROM user";
           return getJdbcTemplate().query(sql, new UserRowMapper());
    }

    @Override
    public List<User> findByProperty(String propName, Object propValue) {
         String sql = "SELECT userId, name, phone, email, address, loginName, role, loginStatus"
                + " FROM user WHERE "+propName+"=?";
         return getJdbcTemplate().query(sql, new UserRowMapper(), propValue);
    }
}
