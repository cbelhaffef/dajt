package com.cbelhaffef.dajt.api;

import com.cbelhaffef.dajt.model.session.SessionItem;
import com.cbelhaffef.dajt.model.session.SessionResponse;
import com.cbelhaffef.dajt.model.user.Login;
import com.cbelhaffef.dajt.model.user.User;
import com.cbelhaffef.dajt.repo.UserRepo;
import com.cbelhaffef.dajt.model.response.OperationResponse;
import com.cbelhaffef.dajt.model.session.SessionItem;
import com.cbelhaffef.dajt.model.session.SessionResponse;
import com.cbelhaffef.dajt.model.user.Login;
import com.cbelhaffef.dajt.repo.UserRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.cbelhaffef.dajt.model.response.OperationResponse.ResponseStatusEnum;

/*
This is a dummy rest controller, for the purpose of documentation (/session) path is map to a filter
 - This will only be invoked if security is disabled
 - If Security is enabled then SessionFilter.java is invoked
 - Enabling and Disabling Security is done at config/applicaton.properties 'security.ignored=/**'
*/

@RestController
@Api(tags = {"Authentication"})
public class SessionController {

    @Autowired
    private UserRepo userRepo;

    @ApiResponses(value = { @ApiResponse(code = 200, message = "Will return a security token, which must be passed in every request", response = SessionResponse.class) })
    @RequestMapping(value = "/session", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SessionResponse newSession(@RequestBody Login login, HttpServletRequest request, HttpServletResponse response) {
        System.out.format("\n /Session Called username=%s\n", login.getUsername());
        User user = userRepo.findOneByUsernameAndPassword(login.getUsername(), login.getPassword()).orElse(null);
        SessionResponse resp = new SessionResponse();
        SessionItem sessionItem = new SessionItem();
        if (user != null){
            System.out.format("\n /User Details=%s\n", user.getFirstname());
            sessionItem.setToken("xxx.xxx.xxx");
            sessionItem.setUsername(user.getUsername());
            sessionItem.setFirstname(user.getFirstname());
            sessionItem.setLastname(user.getLastname());
            sessionItem.setEmail(user.getEmail());
            sessionItem.setUserId(user.getUserId());
            //sessionItem.setRole(user.getRole());

            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
            resp.setOperationMessage("Dummy Login Success");
            resp.setItem(sessionItem);
        }
        else{
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.ERROR);
            resp.setOperationMessage("Login Failed");
        }
        return resp;
    }

}
