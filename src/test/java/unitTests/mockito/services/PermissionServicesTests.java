package unitTests.mockito.services;

import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.CategoryVO;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.user.PermissionVO;
import com.github.GuilhermeBauer.Ecommerce.exceptions.RequiredObjectsNullException;
import com.github.GuilhermeBauer.Ecommerce.model.CategoryModel;
import com.github.GuilhermeBauer.Ecommerce.model.PermissionModel;
import com.github.GuilhermeBauer.Ecommerce.repository.PermissionRepository;
import com.github.GuilhermeBauer.Ecommerce.services.PermissionServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import unitTests.mocks.MockPermission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PermissionServicesTests {
    private static final String PERMISSION_NOT_FOUND_MESSAGE = "No record found by that ID!";
    private static final String REQUIRED_OBJECT_MESSAGE = "It is not allowed persisted a null object!";
    private static final UUID PERMISSION_ID = UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17");
    private MockPermission mockPermission;

    @InjectMocks
    private PermissionServices permissionServices;
    @Mock
    private PermissionRepository permissionRepository;

    @BeforeEach
    void setUpMocks() {
        mockPermission = new MockPermission();
        MockitoAnnotations.openMocks(this);
    }

    // Testing create possibilities
    @Test
    public void testCreateIfHaveHateoasLink() throws Exception {
        PermissionModel entity = mockPermission.mockMEntity(PERMISSION_ID);
        PermissionModel persisted = entity;
        PermissionVO vo = mockPermission.mockVVO(PERMISSION_ID);
        when(permissionRepository.save(entity)).thenReturn(persisted);
        PermissionVO result = permissionServices.create(vo);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        System.out.println(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/permission/270c51f2-0acf-4ca6-bfc3-1c654f0ddd17>;rel=\"self\"]"));
        assertEquals("USER", result.getDescription());

    }

    @Test
    void testCreateWithPermissionIsNull() {
        Exception exception = assertThrows(RequiredObjectsNullException.class,
                () -> permissionServices.create(null));

        assertEquals(REQUIRED_OBJECT_MESSAGE, exception.getMessage());
    }

    // Testing update possibilities

    @Test
    public void testUpdateIfHaveHateoasLink() throws Exception {

        PermissionModel entity = mockPermission.mockMEntity(PERMISSION_ID);
        PermissionModel persisted = entity;
        PermissionVO vo = mockPermission.mockVVO(PERMISSION_ID);
        when(permissionRepository.findById(PERMISSION_ID)).thenReturn(Optional.of(entity));
        when(permissionRepository.save(entity)).thenReturn(persisted);
        PermissionVO result = permissionServices.update(vo);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/permission/270c51f2-0acf-4ca6-bfc3-1c654f0ddd17>;rel=\"self\"]"));
        assertEquals("USER", result.getDescription());
    }

    @Test
    void testUpdateWithPermissionIsNull() {
        Exception exception = assertThrows(RequiredObjectsNullException.class,
                () -> permissionServices.update(null));

        assertEquals(REQUIRED_OBJECT_MESSAGE, exception.getMessage());
    }

    // Testing delete possibilities

    @Test
    void testDelete() throws Exception {
        PermissionModel entity = mockPermission.mockMEntity(PERMISSION_ID);
        when(permissionRepository.findById(PERMISSION_ID)).thenReturn(Optional.of(entity));
        permissionServices.delete(PERMISSION_ID);
    }

    // Testing find by id possibilities

    @Test
    public void testFindByIdIfHaveHateoasLink() throws Exception {
        PermissionModel entity = mockPermission.mockMEntity(PERMISSION_ID);

        when(permissionRepository.findById(PERMISSION_ID)).thenReturn(Optional.of(entity));

        PermissionVO result = permissionServices.findById(PERMISSION_ID);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/permission/270c51f2-0acf-4ca6-bfc3-1c654f0ddd17>;rel=\"self\"]"));
        assertEquals("USER", result.getDescription());

    }

    // Testing find all possibilities

    @Test
    void testFindAllIfHaveHateoasLink() throws Exception {
        List<PermissionModel> permissionModelList = mockPermission.mockMEntityList();
        when(permissionRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(permissionModelList));
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<EntityModel<PermissionVO>> allPermissions = permissionServices.findAll(pageRequest);
        assertEquals(14, permissionModelList.size());

        EntityModel<PermissionVO> permission1 = allPermissions.getContent().get(1);
        assertNotNull(permission1);
        assertNotNull(permission1.getContent().getId());
        assertNotNull(permission1.getLinks());
        assertTrue(permission1.getLinks().hasSize(1));
        assertEquals("USER", permission1.getContent().getDescription());


        EntityModel<PermissionVO> permission7 = allPermissions.getContent().get(7);
        assertNotNull(permission7);
        assertNotNull(permission7.getContent().getId());
        assertNotNull(permission7.getLinks());
        assertTrue(permission7.getLinks().hasSize(1));
        assertEquals("USER", permission7.getContent().getDescription());

        EntityModel<PermissionVO> permission10 = allPermissions.getContent().get(10);
        assertNotNull(permission10);
        assertNotNull(permission10.getContent().getId());
        assertNotNull(permission10.getLinks());
        assertTrue(permission10.getLinks().hasSize(1));
        assertEquals("USER", permission10.getContent().getDescription());

    }
}
