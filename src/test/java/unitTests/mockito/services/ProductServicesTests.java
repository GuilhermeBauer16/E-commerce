package unitTests.mockito.services;

import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.ProductVO;
import com.github.GuilhermeBauer.Ecommerce.exceptions.CategoryNotFound;
import com.github.GuilhermeBauer.Ecommerce.exceptions.ProductNotAvailable;
import com.github.GuilhermeBauer.Ecommerce.exceptions.ProductNotFound;
import com.github.GuilhermeBauer.Ecommerce.exceptions.RequiredObjectsNullException;
import com.github.GuilhermeBauer.Ecommerce.model.ProductModel;
import com.github.GuilhermeBauer.Ecommerce.repository.CategoryRepository;
import com.github.GuilhermeBauer.Ecommerce.repository.ProductRepository;
import com.github.GuilhermeBauer.Ecommerce.services.ProductServices;
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
import unitTests.mocks.MockProduct;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class ProductServicesTests {


    private MockProduct mockProduct;

    @InjectMocks
    private ProductServices productServices;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUpMocks() {
        mockProduct = new MockProduct();
        MockitoAnnotations.openMocks(this);
    }

    // Testing create possibilities
    @Test
    void testCreateWithHateoasContainsTheLink() throws Exception {
        ProductModel entity = mockProduct.mockProductEntity(
                UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17"));

        ProductModel persited = entity;
        persited.setId(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17"));

        ProductVO vo = mockProduct.mockProductVo(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17"));
        vo.setId(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17"));
        when(categoryRepository.findByName(entity.getCategoryModel().getName().toUpperCase())).thenReturn(persited.getCategoryModel());
        when(productRepository.save(entity)).thenReturn(persited);
        ProductVO result = productServices.create(vo);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/product/270c51f2-0acf-4ca6-bfc3-1c654f0ddd17>;rel=\"self\"]"));
        assertEquals("Air max",result.getName());
        assertEquals("the shoes",result.getDescription());
        assertEquals("nike", result.getBranch());
        assertEquals("SHOES", result.getCategoryModel().getName());
        assertEquals(100.00,result.getPrice());
        assertEquals(10, result.getQuantity());
        assertEquals(true,result.getAvailable());


    }

    @Test
    void testCreteIfIsNull(){



        Exception exception = assertThrows(RequiredObjectsNullException.class,
                () -> productServices.create(null));

        String expectedMessage = "It is not allowed persisted a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    // Testing update possibilities

    @Test
    void testUpdateIfHaveHateoasLink() throws Exception {
        UUID productID = UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17");
        ProductModel entity = mockProduct.mockProductEntity(productID);
        ProductModel persisted = entity;
        persisted.setId(productID);
        ProductVO productVO = mockProduct.mockProductVo(productID);
        productVO.setId(productID);
        when(categoryRepository.findByName(entity.getCategoryModel().getName().toUpperCase()))
                .thenReturn(persisted.getCategoryModel());
        when(productRepository.findById(productID)).thenReturn(Optional.of(persisted));
        when(productRepository.save(entity)).thenReturn(persisted);
        ProductVO result = productServices.update(productVO);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/product/270c51f2-0acf-4ca6-bfc3-1c654f0ddd17>;rel=\"self\"]"));
        assertEquals("Air max",result.getName());
        assertEquals("the shoes",result.getDescription());
        assertEquals("nike", result.getBranch());
        assertEquals("SHOES", result.getCategoryModel().getName());
        assertEquals(100.00,result.getPrice());
        assertEquals(10, result.getQuantity());
        assertEquals(true,result.getAvailable());

    }

    @Test
    void testUpdateIfProductIsNull(){
        Exception exception = assertThrows(RequiredObjectsNullException.class,
                () -> productServices.update(null));

        String expectedMessage = "It is not allowed persisted a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void testUpdateIfCategoryIsNull(){
        UUID productID = UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17");
        ProductVO vo = mockProduct.mockProductVo(productID);
        vo.setCategoryModel(null);
        Exception exception = assertThrows(CategoryNotFound.class,
                () -> productServices.update(vo));
        String expectedMessage = "No category was found for that Product!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    // Testing find all possibilities

    @Test

    void testFindAllIfHaveHateoasLink() throws Exception {
        UUID productID = UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17");
        List<ProductModel> productEntityList = mockProduct.mockProductEntityList();
        when(productRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(productEntityList));
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<EntityModel<ProductVO>> allProduct = productServices.findAll(pageRequest);
        assertEquals(14, allProduct.getTotalElements());

        EntityModel<ProductVO> product1 = allProduct.getContent().get(1);
        assertNotNull(product1);
        assertNotNull(product1.getContent().getId());
        assertNotNull(product1.getContent().getLinks());
        assertTrue(product1.toString().contains("links: [</api/product/270c51f2-0acf-4ca6-bfc3-1c654f0ddd17>;rel=\"self\"]"));
        assertEquals("Air max",product1.getContent().getName());
        assertEquals("the shoes",product1.getContent().getDescription());
        assertEquals("nike", product1.getContent().getBranch());
        assertEquals("SHOES", product1.getContent().getCategoryModel().getName());
        assertEquals(100.00,product1.getContent().getPrice());
        assertEquals(10, product1.getContent().getQuantity());
        assertEquals(true,product1.getContent().getAvailable());

        EntityModel<ProductVO> product7 = allProduct.getContent().get(7);
        assertNotNull(product7);
        assertNotNull(product7.getContent().getId());
        assertNotNull(product7.getContent().getLinks());
        assertTrue(product7.toString().contains("links: [</api/product/270c51f2-0acf-4ca6-bfc3-1c654f0ddd17>;rel=\"self\"]"));
        assertEquals("Air max",product7.getContent().getName());
        assertEquals("the shoes",product7.getContent().getDescription());
        assertEquals("nike", product7.getContent().getBranch());
        assertEquals("SHOES", product7.getContent().getCategoryModel().getName());
        assertEquals(100.00,product7.getContent().getPrice());
        assertEquals(10, product7.getContent().getQuantity());
        assertEquals(true,product7.getContent().getAvailable());

        EntityModel<ProductVO> product10 = allProduct.getContent().get(10);
        assertNotNull(product10);
        assertNotNull(product10.getContent().getId());
        assertNotNull(product10.getContent().getLinks());
        assertTrue(product10.toString().contains("links: [</api/product/270c51f2-0acf-4ca6-bfc3-1c654f0ddd17>;rel=\"self\"]"));
        assertEquals("Air max",product10.getContent().getName());
        assertEquals("the shoes",product10.getContent().getDescription());
        assertEquals("nike", product10.getContent().getBranch());
        assertEquals("SHOES", product10.getContent().getCategoryModel().getName());
        assertEquals(100.00,product10.getContent().getPrice());
        assertEquals(10, product10.getContent().getQuantity());
        assertEquals(true,product10.getContent().getAvailable());

    }

    // Testing find by id possibilities

    @Test
    void testFindByIdIfHaveHateoasLink() throws Exception {
        System.out.println(new Date());
        UUID productID = UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17");
        ProductModel entity = mockProduct.mockProductEntity(productID);
        entity.setId(productID);
        when(productRepository.findById(productID)).thenReturn(Optional.of(entity));
        ProductVO result = productServices.findById(productID);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        System.out.println("Links: " + result.getLinks());
        assertTrue(result.toString().contains("links: [</api/product/270c51f2-0acf-4ca6-bfc3-1c654f0ddd17>;rel=\"self\"]"));
        assertEquals("Air max",result.getName());
        assertEquals("the shoes",result.getDescription());
        assertEquals("nike", result.getBranch());
        assertEquals("SHOES", result.getCategoryModel().getName());
        assertEquals(100.00,result.getPrice());
        assertEquals(10, result.getQuantity());
        assertEquals(true,result.getAvailable());

    }

    @Test
    void testFindByIdIfProductIsNull(){
        Exception exception = assertThrows(ProductNotFound.class,
                () -> productServices.findById(null));

        String expectedMessage = "No product was found for that ID!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    // Testing delete possibilities

    @Test
    void testDelete() throws Exception {
        UUID productID = UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17");
        ProductModel entity = mockProduct.mockProductEntity(productID);
        when(productRepository.findById(productID)).thenReturn(Optional.ofNullable(entity));
        productServices.delete(productID);



    }

    // Testing is available possibilities

    @Test
    void testIsAvailableIfQuantityIsLowerThan0(){
        UUID productID = UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17");
        ProductModel entity = mockProduct.mockProductEntity(productID);
        entity.setQuantity(0);

        assertThrows(ProductNotAvailable.class,
                () -> productServices.isAvailable(entity));
    }








}
