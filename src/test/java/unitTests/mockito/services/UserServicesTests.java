package unitTests.mockito.services;

import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.user.PermissionVO;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.user.UserVO;
import com.github.GuilhermeBauer.Ecommerce.exceptions.RequiredObjectsNullException;
import com.github.GuilhermeBauer.Ecommerce.model.PermissionModel;
import com.github.GuilhermeBauer.Ecommerce.model.UserModel;
import com.github.GuilhermeBauer.Ecommerce.repository.PermissionRepository;
import com.github.GuilhermeBauer.Ecommerce.repository.UserRepository;
import com.github.GuilhermeBauer.Ecommerce.services.UserServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import unitTests.mocks.MockPermission;
import unitTests.mocks.MockUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class UserServicesTests {
    private static final String PERMISSION_NOT_FOUND_MESSAGE = "No record found by that ID!";
    private static final String REQUIRED_OBJECT_MESSAGE = "It is not allowed persisted a null object!";

    private static final String USERNAME_NOT_FOUND_MESSAGE = "Not records founds for that username!";
    private static final String ILLEGAL_ARGUMENT_MESSAGE = "UserVO or its permission is null";
    private static final UUID USER_ID = UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17");
    private MockUser mockUser;

    private MockPermission mockPermission;

    @InjectMocks
    private UserServices userServices;
    @Mock
    private UserRepository userRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private PasswordEncoder encoder;

    @BeforeEach
    void setUpMocks() {
        mockUser = new MockUser();
        mockPermission = new MockPermission();

        MockitoAnnotations.openMocks(this);
    }

    // Testing create possibilities
    @Test
    public void testCreateIfHaveHateoasLink() throws Exception {
        UserModel entity = mockUser.mockMEntity(USER_ID);
        UserModel persisted = entity;
        PermissionModel permissionModel = mockPermission.mockMEntity(USER_ID);
        UserVO vo = mockUser.mockVVO(USER_ID);
        when(permissionRepository.findById(USER_ID)).thenReturn(Optional.of(permissionModel));
        when(encoder.encode("123")).thenReturn(entity.getPassword());
        when(userRepository.save(entity)).thenReturn(persisted);
        UserVO result = userServices.create(vo);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        System.out.println(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/user>;rel=\"self\"]"));
        assertEquals("John", result.getUsername());
        assertEquals("123", result.getPassword());
        assertEquals(true, result.getAccountNonExpired());
        assertEquals(true, result.getAccountNonLocked());
        assertEquals(true, result.getAccountNonExpired());
        assertEquals(true, result.getEnabled());
        assertEquals(permissionModel, result.getPermission());

    }

    @Test
    void testCreateWithDescriptionIsNull() {
        UserVO vo = mockUser.mockVVO(USER_ID);
        vo.getPermission().setDescription(null);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> userServices.create(vo));

        assertEquals(ILLEGAL_ARGUMENT_MESSAGE, exception.getMessage());
    }

    @Test
    void testCreateWithUserIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> userServices.create(null));

        assertEquals(ILLEGAL_ARGUMENT_MESSAGE, exception.getMessage());
    }

    @Test
    void testCreateWithPermissionIsNull() {
        UserVO vo = mockUser.mockVVO(USER_ID);
        vo.setPermission(null);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> userServices.create(vo));

        assertEquals(ILLEGAL_ARGUMENT_MESSAGE, exception.getMessage());
    }

    // Testing update possibilities

    @Test
    public void testUpdateIfHaveHateoasLink() throws Exception {
        UserModel entity = mockUser.mockMEntity(USER_ID);
        UserModel persisted = entity;
        PermissionModel permissionModel = mockPermission.mockMEntity(USER_ID);
        UserVO vo = mockUser.mockVVO(USER_ID);
        when(userRepository.findByUsername("John")).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(persisted);
        UserVO result = userServices.update(vo);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        System.out.println(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/user>;rel=\"self\"]"));
        assertEquals("John", result.getUsername());
        assertEquals("123", result.getPassword());
        assertEquals(true, result.getAccountNonExpired());
        assertEquals(true, result.getAccountNonLocked());
        assertEquals(true, result.getAccountNonExpired());
        assertEquals(true, result.getEnabled());
        assertEquals(permissionModel, result.getPermission());

    }

    @Test
    void testUpdateWithUserIsNull() {
        Exception exception = assertThrows(RequiredObjectsNullException.class,
                () -> userServices.update(null));

        assertEquals(REQUIRED_OBJECT_MESSAGE, exception.getMessage());
    }

    @Test
    void testUpdateWithUserNameIsNull() {
        UserModel entity = mockUser.mockMEntity(USER_ID);
        entity.setUsername(null);
        UserVO vo = mockUser.mockVVO(USER_ID);
        vo.setUsername("Test");
        when(userRepository.findByUsername("Test")).thenReturn(null);
        Exception exception = assertThrows(UsernameNotFoundException.class,
                () -> userServices.update(vo));

        assertEquals(USERNAME_NOT_FOUND_MESSAGE, exception.getMessage());
    }

    // Testing find by name possibilities

    @Test
    void testFindByName() throws Exception {
        UserModel entity = mockUser.mockMEntity(USER_ID);
        UserVO vo = mockUser.mockVVO(USER_ID);
        when(userRepository.findByUsername("John")).thenReturn(entity);
        UserVO result = userServices.findByName(entity.getUsername());
        assertEquals(vo,result);


    }
    @Test
    void testFindByNameWIthUsernameEmpty() {
        String username = "";
        Exception exception = assertThrows(UsernameNotFoundException.class,
                () -> userServices.findByName(username));

        assertEquals(USERNAME_NOT_FOUND_MESSAGE, exception.getMessage());

    }

    //     Testing delete possibilities
    @Test
    void testDelete() throws Exception {
        UserModel entity = mockUser.mockMEntity(USER_ID);
        when(userRepository.findByUsername("John")).thenReturn(entity);
        userServices.delete(entity.getUsername());
    }

    @Test
    void testLoadUserByUsernameWithUsernameIsWrong() {
        String username = "Test";
        when(userRepository.findByUsername("Test")).thenReturn(null);
        Exception exception = assertThrows(UsernameNotFoundException.class,
                () -> userServices.loadUserByUsername(username));

        assertEquals(USERNAME_NOT_FOUND_MESSAGE, exception.getMessage());

    }
    @Test
    void testLoadUserByUsername() throws Exception {
        UserModel entity = mockUser.mockMEntity(USER_ID);
        UserVO vo = mockUser.mockVVO(USER_ID);
        when(userRepository.findByUsername("John")).thenReturn(entity);
        UserDetails result = userServices.loadUserByUsername(entity.getUsername());
        assertEquals(entity,result);


    }
}




