package unitTests.mockito.services;

import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.security.TokenVO;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.user.UserVO;
import com.github.GuilhermeBauer.Ecommerce.model.UserModel;
import com.github.GuilhermeBauer.Ecommerce.repository.UserRepository;
import com.github.GuilhermeBauer.Ecommerce.security.jwt.JwtTokenProvider;
import com.github.GuilhermeBauer.Ecommerce.services.AuthServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import unitTests.mocks.MockToken;
import unitTests.mocks.MockUser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class AuthServicesTests {
    private static final UUID AUTH_ID = UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17");

    private static final String USERNAME_NOT_FOUND_MESSAGE = "Not records founds for that username!";
    private static final String BAD_CREDENTIALS_MESSAGE = "Invalid username/password supplied";
    private MockUser mockUser;

    private MockToken mockToken;
    @InjectMocks
    private AuthServices authServices;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        mockUser = new MockUser();
        mockToken = new MockToken();
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testSingInWithUserFound() throws ParseException {

        UserModel entity = mockUser.mockMEntity(AUTH_ID);
        when(userRepository.findByUsername("John")).thenReturn(entity);
        when(passwordEncoder.matches("123", entity.getPassword())).thenReturn(true);
        TokenVO tokenVO = mockToken.mockVVO();
        when(jwtTokenProvider.createAccessToken("John", entity.getRoles())).thenReturn(tokenVO);
        ResponseEntity<?> responseEntity = authServices.signin(entity);
        assertNotNull(responseEntity);

    }

    @Test
    void testSignInWithUserNotFound() {
        UserModel entity = mockUser.mockMEntity(AUTH_ID);
        when(userRepository.findByUsername("test")).thenReturn(null);
        Exception exception = assertThrows(BadCredentialsException.class,
                () -> authServices.signin(entity));

        assertEquals(BAD_CREDENTIALS_MESSAGE,exception.getMessage());

    }

    @Test
    void testRefreshTokenUserFound() throws ParseException {
        String username = "John";
        String refreshToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaWx2YSIsInJv" +
                "bGVzIjpbIlVTRVIiXSwiZXhwIjoxNzExMDcxNjg5LCJpYXQiOjE3MTEwNjA4ODl9.JsT1iuPyD0R" +
                "vY-vKToQaO2dVZMMTUZlZqI-ydadEPW4";
        UserModel entity = mockUser.mockMEntity(AUTH_ID);
        when(userRepository.findByUsername("John")).thenReturn(entity);
        TokenVO tokenVO = mockToken.mockVVO();
        when(jwtTokenProvider.createRefreshToken(refreshToken)).thenReturn(tokenVO);
        ResponseEntity response = authServices.refreshToken(username,refreshToken);
        assertEquals(ResponseEntity.ok(tokenVO),response);
    }

    @Test
    void testRefreshTokenWithUserNameIsWrong(){

        // Arrange
        String username = "John";
        String refreshToken = "some_refresh_token";
        when(userRepository.findByUsername(username)).thenReturn(null);

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> authServices.refreshToken(username, refreshToken)
        );

        // Assert
        assertEquals(USERNAME_NOT_FOUND_MESSAGE, exception.getMessage());
    }

    @Test
    void testCheckIfUserIsNull(){

        String username = "Test";
        when(userRepository.findByUsername(username)).thenReturn(null);

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> authServices.checkIfUserIsnull(username)
        );

        // Assert
        assertEquals(USERNAME_NOT_FOUND_MESSAGE, exception.getMessage());
    }
}
