package com.app.api.user;

import com.app.model.folder.FolderListResponse;
import com.app.model.victim.Victim;
import com.app.model.victim.VictimListResponse;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import com.google.common.base.Strings;
import org.apache.commons.io.IOUtils;
import org.json.*;

import com.app.model.response.*;
import com.app.model.user.*;
import static com.app.model.response.OperationResponse.*;

@RestController
@Api(tags = {"Authentication"})
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
            resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
		}
		else {
            resp.setOperationStatus(ResponseStatusEnum.NO_ACCESS);
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
      resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
			resp.setOperationMessage("User Added");
		}
		else{
      resp.setOperationStatus(ResponseStatusEnum.ERROR);
			resp.setOperationMessage("Unable to add user");
		}
		return resp;
	}

    @ApiOperation(value = "List of users", response = FolderListResponse.class)
    @RequestMapping(value = "/users", method = RequestMethod.GET , produces={"application/json; charset=UTF-8"})
    public UserListResponse getVictims(Pageable pageable){
        UserListResponse resp = new UserListResponse();
        Page<User> pg = userService.find;
        resp.setPageStats(pg, true);
        resp.setItems(pg.getContent());
        return resp;
    }

}
