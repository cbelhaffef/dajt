package com.cbelhaffef.dajt.api.user;

import com.cbelhaffef.dajt.dao.entities.User;
import com.cbelhaffef.dajt.dao.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

	public String getLoggedInUsername(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth==null){
            return "nosession";
        }
		return auth.getName();
	}


	public User getLoggedInUser() {
		String loggedInUsername = this.getLoggedInUsername();
		System.out.format("\n1. Inside >> getLoggedInUser: %s", loggedInUsername);
		User user = this.getUserInfoByUsername(loggedInUsername);
		System.out.format("\n2. After Find User: %s", loggedInUsername);
		return user;
	}

	public User getUserInfoByUsername(String username){
			User user = this.userRepo.findOneByUsername(username).orElseGet( () -> new User());
			return user;
	}

	public boolean insertOrSaveUser(User user) {
		this.userRepo.save(user);
		return true;
	}

	public boolean addNewUser(User user) {
		User newUser = this.getUserInfoByUsername(user.getUsername());
		if (newUser.getUsername().equals("new")){
			// This means the username is not found therfore its is returning a default value of "new"
			return this.insertOrSaveUser(user);
		}
		else{
			return false;
		}
	}

	public User getUserById(Long id){
	    return userRepo.findOne(id);
}

	public Page<User> getUsers(Pageable pageable){
	    return userRepo.findAll(pageable);
    }
}
