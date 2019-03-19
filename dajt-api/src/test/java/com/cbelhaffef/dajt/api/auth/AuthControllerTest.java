package com.cbelhaffef.dajt.api.auth;

import com.cbelhaffef.dajt.api.role.RoleCodeEnum;
import com.cbelhaffef.dajt.api.user.LoginForm;
import com.cbelhaffef.dajt.security.jwt.JwtAuthTokenFilter;
import com.cbelhaffef.dajt.security.service.UserDetailsService;
import com.cbelhaffef.dajt.security.service.UserPrinciple;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Autowired
    @InjectMocks
    private AuthController authController;

    @Mock
    private UserDetailsService userDetailsService;

    @Autowired
    @InjectMocks
    private JwtAuthTokenFilter jwtAuthTokenFilter;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private String username = "john";
    private String password = "johnPassword";

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        UsernamePasswordAuthenticationToken userGiven =  new UsernamePasswordAuthenticationToken(username, password);
        UserPrinciple userExpected = new UserPrinciple(1L, username, "", password, "","",null);
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(RoleCodeEnum.ROLE_USER.name());
        userExpected.setAuthorities(authorities);
        TestingAuthenticationToken authenticationExpected = new TestingAuthenticationToken(userExpected,authorities);
        when(authenticationManager.authenticate(userGiven)).thenReturn(authenticationExpected);

        when(userDetailsService.loadUserByUsername(username)).thenReturn(userExpected);

        mockMvc = MockMvcBuilders.webAppContextSetup(context).addFilters(jwtAuthTokenFilter).build();
    }

    @Ignore
    @Test
    public void shouldNotAllowAccessToUnauthenticatedUser() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/roles")).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldGenerateAuthToken() throws Exception {
        ResponseEntity<SessionResponse> response = (ResponseEntity<SessionResponse>) authController.authenticateUser(new LoginForm(username,password));
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getItem());
        assertNotNull(response.getBody().getItem().getToken());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/roles").header("Authorization", response.getBody().getItem().getToken())).andExpect(status().isOk());
    }
}
