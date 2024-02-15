package unitTests.mockito.services;

import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.ProductVO;
import com.github.GuilhermeBauer.Ecommerce.model.ProductModel;
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
import unitTests.mocks.MockProduct;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class ProductServicesTests {


    private MockProduct mockProduct;

    @InjectMocks
    private ProductServices productServices;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUpMocks() {
        mockProduct = new MockProduct();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateWithHateoasContainsTheLink() throws Exception {
        ProductModel entity = mockProduct.mockProductEntity(
                UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17"));

        ProductModel persited = entity;
        persited.setId(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17"));

        ProductVO vo = mockProduct.mockProductVo(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17"));
        vo.setId(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17"));
        when(productRepository.save(entity)).thenReturn(persited);
        ProductVO result = productServices.create(vo);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
//        assertTrue(result.toString().contains());
//        assertEquals("Air max",result.getName());
//        assertEquals("the shoes",result.getDescription());
//        assertEquals("nike", result.getBranch());
//        assertEquals(100.00,result.getPrice());
//        assertEquals(10, result.getQuantity());
//        assertEquals(true,result.getAvailable());


    }
}
