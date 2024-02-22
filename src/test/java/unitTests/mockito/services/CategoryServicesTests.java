package unitTests.mockito.services;

import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.CategoryVO;
import com.github.GuilhermeBauer.Ecommerce.exceptions.RequiredObjectsNullException;
import com.github.GuilhermeBauer.Ecommerce.model.CategoryModel;
import com.github.GuilhermeBauer.Ecommerce.repository.CategoryRepository;
import com.github.GuilhermeBauer.Ecommerce.services.CategoryServices;
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
import unitTests.mocks.MockCategory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class CategoryServicesTests {

    private MockCategory mockCategory;

    @InjectMocks
    private CategoryServices categoryServices;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUpMocks() {
        mockCategory = new MockCategory();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateWithNullCategory(){

        Exception exception = assertThrows(RequiredObjectsNullException.class,() ->{
            categoryServices.create(null);
        });

        String expectedMessage = "It is not allowed persisted a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateWithNullCategory(){
        Exception exception = assertThrows(RequiredObjectsNullException.class,
                () -> {categoryServices.update(null);});
        String expectedMessage = "It is not allowed persisted a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));


    }

    @Test
    void TestFindByIdIfHaveHateoasLink() throws Exception {
        UUID categoryId = UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17");
        CategoryModel entity = mockCategory.mockCategoryEntity(categoryId);
        entity.setId(categoryId);
        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(entity));

        CategoryVO result = categoryServices.findById(categoryId);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</category/270c51f2-0acf-4ca6-bfc3-1c654f0ddd17>;rel=\"self\"]"));
        assertEquals("SHOES", result.getName());

    }

    @Test
    void TestCreateIfHaveHateoasLink() throws Exception {
        UUID categoryId = UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17");
        CategoryModel entity = mockCategory.mockCategoryEntity(categoryId);
        entity.setId(categoryId);

        CategoryModel persisted = entity;
        persisted.setId(categoryId);

        CategoryVO vo = mockCategory.mockCategoryVO(categoryId);
        vo.setId(categoryId);

        when(categoryRepository.save(entity)).thenReturn(persisted);
        CategoryVO result = categoryServices.create(vo);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</category/270c51f2-0acf-4ca6-bfc3-1c654f0ddd17>;rel=\"self\"]"));
        assertEquals("SHOES", result.getName());
    }

    @Test
    void TestUpdateIfHaveHateoasLink() throws Exception {

        UUID categoryId = UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17");
        CategoryModel entity = mockCategory.mockCategoryEntity(categoryId);
        entity.setId(categoryId);

        CategoryModel persisted = entity;
        persisted.setId(categoryId);

        CategoryVO vo = mockCategory.mockCategoryVO(categoryId);
        vo.setId(categoryId);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(entity));
        when(categoryRepository.save(entity)).thenReturn(persisted);

        CategoryVO result = categoryServices.update(vo);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</category/270c51f2-0acf-4ca6-bfc3-1c654f0ddd17>;rel=\"self\"]"));
        assertEquals("SHOES", result.getName());
    }

    @Test
    void testDelete() throws Exception {
        UUID categoryId = UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17");
        CategoryModel entity = mockCategory.mockCategoryEntity(categoryId);
        entity.setId(categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(entity));
        categoryServices.delete(categoryId);
    }

    @Test
    void testFindAllIfHavePageable(){
        List<CategoryModel> categoryModelList = mockCategory.mockCategoryEntityList();
        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(categoryModelList));
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<EntityModel<CategoryVO>> allCategory = categoryServices.findAll(pageRequest);
        assertEquals( 14, categoryModelList.size());


    }

}