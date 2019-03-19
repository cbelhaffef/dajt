package com.cbelhaffef.dajt.api.auth;

import com.cbelhaffef.dajt.api.office.Office;
import com.cbelhaffef.dajt.api.office.OfficeRepo;
import com.cbelhaffef.dajt.api.role.Role;
import com.cbelhaffef.dajt.api.role.RoleCodeEnum;
import com.cbelhaffef.dajt.api.role.RoleRepo;
import com.cbelhaffef.dajt.api.user.LoginForm;
import com.cbelhaffef.dajt.api.user.SignUpForm;
import com.cbelhaffef.dajt.api.user.User;
import com.cbelhaffef.dajt.api.user.UserRepo;
import com.cbelhaffef.dajt.security.jwt.JwtProvider;
import com.cbelhaffef.dajt.security.service.UserPrinciple;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.cbelhaffef.dajt.api.auth.OperationResponse.ResponseStatusEnum.ERROR;
import static com.cbelhaffef.dajt.api.auth.OperationResponse.ResponseStatusEnum.SUCCESS;


@RestController
@Api(tags = {"Authentication"})
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private OfficeRepo officeRepo;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/signin")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Will return a security token, which must be passed in every request", response = SessionResponse.class) })
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginForm){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));

        if(authentication != null){

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateJwtToken(authentication);
            UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();

            SessionData sessionData = new SessionData(jwt, userDetails);

            return ResponseEntity.ok(new SessionResponse("Authentication Successful",sessionData, SUCCESS));
        }else{
            return new ResponseEntity<>(new SessionResponse("username or password not correct",ERROR),HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if (userRepo.findOneByUsername(signUpRequest.getUsername()).isPresent()) {
            return new ResponseEntity<>(new SessionResponse("Fail -> Username is already taken!",ERROR),HttpStatus.BAD_REQUEST);
        }

        if (userRepo.findOneByEmail(signUpRequest.getEmail()).isPresent()) {
            return new ResponseEntity<>(new SessionResponse("Fail -> Email is already in use!",ERROR), HttpStatus.BAD_REQUEST);
        }

        Optional<Office> officeOptional = officeRepo.findOneByCode(signUpRequest.getOffice());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepo.findByCode(RoleCodeEnum.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(adminRole);

                    break;
                default:
                    Role userRole = roleRepo.findByCode(RoleCodeEnum.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(userRole);
            }
        });

        // Creating user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getFirstname(),
                signUpRequest.getLastname(),
                signUpRequest.getEmail(),
                officeOptional.isPresent() ? officeOptional.get() : null,
                signUpRequest.getPassword(),
                roles,
                false);

        userRepo.save(user);

        return ResponseEntity.ok(new SessionResponse("User registered successfully!",SUCCESS));
    }

}
