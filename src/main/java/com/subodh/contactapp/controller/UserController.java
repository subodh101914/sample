package com.subodh.contactapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.subodh.contactapp.command.LoginDTO;
import com.subodh.contactapp.command.UserDTO;
import com.subodh.contactapp.domain.User;
import com.subodh.contactapp.exceptions.UserBlockedException;
import com.subodh.contactapp.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userservice;

	@RequestMapping(value = { "/", "/index" })
	public String helloworld(@ModelAttribute("command") LoginDTO logindto) {
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String handleLogin(@ModelAttribute("command") LoginDTO cmd, Model m, HttpSession session) {
		try {
			User loggedInUser = userservice.login(cmd.getLoginname(), cmd.getPassword());
			if (loggedInUser == null) {
				// FAILED
				// add error message and go back to login-form
				m.addAttribute("err", "Login Failed! Enter valid credentials.");
				return "index";// JSP - Login FORM
			} else // SUCCESS
			// check the role and redirect to a appropriate dashboard
			{
				if (loggedInUser.getRole().equals(UserService.ROLE_ADMIN)) {
					// add user detail in session (assign session to logged in user)
					addUserInSession(loggedInUser, session);
					return "redirect:admin/dashboard";
				} else if (loggedInUser.getRole().equals(UserService.ROLE_USER)) {
					// add user detail in session (assign session to logged in user)
					addUserInSession(loggedInUser, session);
					return "redirect:user/dashboard";
				} else {
					// add error message and go back to login-form
					m.addAttribute("err", "Invalid User ROLE");
					return "index";// JSP - Login FORM
				}
			}
		} catch (UserBlockedException ex) {
			// add error message and go back to login-form
			m.addAttribute("err", ex.getMessage());
			return "index";// JSP - Login FORM
		}
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:index?act=lo";
	}
	
	@RequestMapping(value = "/reg_form")
    public String registrationForm(Model m) {
        UserDTO cmd = new UserDTO();
        m.addAttribute("command", cmd);
        return "reg_form";//JSP
    }
	
	@RequestMapping(value = "/register")
    public String registration(@ModelAttribute("command") UserDTO cmd, Model m) {
        try {
        	User user = cmd.getUser();
        	user.setRole(userservice.ROLE_USER);
        	user.setLoginStatus(userservice.LOGIN_STATUS_ACTIVE);
        	userservice.register(user);
        }
        catch (DuplicateKeyException e) {
            e.printStackTrace();
            m.addAttribute("err", "Username is already registered. Please select another username.");
            return "reg_form";//JSP
        }
        return "redirect:index?act=reg";//JSP
    }

	@RequestMapping(value = { "/user/dashboard" })
	public String userdashboard() {
		return "dashboard-user";
	}

	@RequestMapping(value = { "/admin/dashboard" })
	public String admindashboard() {
		return "dashboard-admin";
	}

	private void addUserInSession(User u, HttpSession session) {
		session.setAttribute("user", u);
		session.setAttribute("userId", u.getUserId());
		session.setAttribute("role", u.getRole());
	}
}
