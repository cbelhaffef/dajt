package com.cbelhaffef.dajt.api.user;

import com.cbelhaffef.dajt.api.auth.OperationResponse;
import com.cbelhaffef.dajt.api.folder.FolderListResponse;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"User"})
public class UserController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Gets current user information", response = UserResponse.class)
	@RequestMapping(value = "/user", method = RequestMethod.GET, produces = {"application/json"})
	public UserResponse getUserInformation(@RequestParam(value = "name", required = false) String usernameParam, HttpServletRequest req) {

		String loggedInUsername = userService.getLoggedInUsername();

		User user;
		boolean provideUserDetails = false;

		if (Strings.isNullOrEmpty(usernameParam)) {
			provideUserDetails = true;
			user = userService.getLoggedInUser();
		}
		else if (loggedInUsername.equals(usernameParam)) {
			provideUserDetails = true;
			user = userService.getLoggedInUser();
		}
		else {
			//Check if the current user is superuser then provide the details of requested user
			provideUserDetails = true;
			user = userService.getUserInfoByUsername(usernameParam);
		}

		UserResponse resp = new UserResponse();
		if (provideUserDetails) {
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
		}
		else {
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.NO_ACCESS);
			resp.setOperationMessage("No Access");
		}
		resp.setData(user);
		return resp;
	}


	@ApiOperation(value = "Add new user", response = OperationResponse.class)
	@RequestMapping(value = "/user", method = RequestMethod.POST, produces = {"application/json"})
	public OperationResponse addNewUser(@RequestBody User user, HttpServletRequest req) {
		boolean userAddSuccess = userService.addNewUser(user);
		OperationResponse resp = new OperationResponse();
		if (userAddSuccess==true){
      resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
			resp.setOperationMessage("User Added");
		}
		else{
      resp.setOperationStatus(OperationResponse.ResponseStatusEnum.ERROR);
			resp.setOperationMessage("Unable to add user");
		}
		return resp;
	}

    @ApiOperation(value = "List of users", response = FolderListResponse.class)
    @RequestMapping(value = "/users", method = RequestMethod.GET , produces={"application/json; charset=UTF-8"})
    public UserListResponse getUsers(Pageable pageable){
        UserListResponse resp = new UserListResponse();
        Page<User> pg = userService.getUsers(pageable);
        resp.setPageStats(pg, true);
        resp.setItems(pg.getContent());
        return resp;
    }

}
