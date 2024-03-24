package unitTests.mockito.services;

import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.CartItemVO;
import com.github.GuilhermeBauer.Ecommerce.exceptions.CartItemNotFound;
import com.github.GuilhermeBauer.Ecommerce.exceptions.InsufficientQuantityAvailable;
import com.github.GuilhermeBauer.Ecommerce.exceptions.RequiredObjectsNullException;
import com.github.GuilhermeBauer.Ecommerce.model.CartItemModel;
import com.github.GuilhermeBauer.Ecommerce.model.ProductModel;
import com.github.GuilhermeBauer.Ecommerce.repository.CartItemRepository;
import com.github.GuilhermeBauer.Ecommerce.repository.ProductRepository;
import com.github.GuilhermeBauer.Ecommerce.services.CartItemServices;
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
import unitTests.mocks.MockCartItem;
import unitTests.mocks.MockProduct;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class CartItemServicesTests {
    private static final String CART_ITEM_NOT_FOUND_MESSAGE = "No cart item was found for that ID!";
    private static final String REQUIRED_OBJECT_MESSAGE = "It is not allowed persisted a null object!";

    private static final String QUANTITY_MESSAGE = "Please type a quantity plus to 0!";

    private static final String INSUFFICIENT_AVAILABLE_QUANTITY_MESSAGE = "Don't have enough quantity available";


    private MockCartItem mockCartItem;
    private MockProduct mockProduct;


    @InjectMocks
    private CartItemServices cartItemServices;
    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUpMocks() {
        mockCartItem = new MockCartItem();
        mockProduct = new MockProduct();
        MockitoAnnotations.openMocks(this);
    }

    // Testing create possibilities
    @Test
    public void testCreateIfHaveHateoasLink() throws Exception {
        UUID cartItemId = UUID.fromString("136be68b-6916-4f39-8ef9-19f2267a88f6");
        CartItemModel entity = mockCartItem.mockCartItemEntity(cartItemId);
        CartItemModel persisted = entity;
        CartItemVO vo = mockCartItem.mockCartItemVO(cartItemId);
        ProductModel productVO = mockProduct.mockProductEntity(cartItemId);

        when(productRepository.findById(cartItemId)).thenReturn(Optional.of(productVO));
        when(cartItemRepository.save(entity)).thenReturn(persisted);

        CartItemVO result = cartItemServices.create(vo);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().hasSize(1));
        assertEquals(productVO, result.getProduct());
        assertEquals(10, result.getQuantity());
    }

    @Test
    void testCreateIfCartItemIdIsNull() throws Exception {
        Exception exception = assertThrows(RequiredObjectsNullException.class,
                () -> cartItemServices.create(null));

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(REQUIRED_OBJECT_MESSAGE));
    }

    // Testing update possibilities
    @Test
    void testUpdateIfHaveHateoasLink() throws Exception {
        UUID cartItemId = UUID.fromString("136be68b-6916-4f39-8ef9-19f2267a88f6");
        CartItemModel entity = mockCartItem.mockCartItemEntity(cartItemId);
        CartItemModel persisted = entity;
        CartItemVO vo = mockCartItem.mockCartItemVO(cartItemId);
        vo.setQuantity(5);
        ProductModel productModel = mockProduct.mockProductEntity(cartItemId);

        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.of(persisted));
        when(cartItemRepository.save(entity)).thenReturn(persisted);

        CartItemVO result = cartItemServices.update(vo);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().hasSize(1));
        assertEquals(productModel, result.getProduct());
        assertEquals(5, result.getQuantity());

    }

    @Test
    void testUpdateIfCartItemIdIsNull() throws Exception {
        Exception exception = assertThrows(RequiredObjectsNullException.class,
                () -> cartItemServices.update(null));

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(REQUIRED_OBJECT_MESSAGE));
    }


    // Testing find by id  possibilities
    @Test
    void testFindByIdIfHaveHateoasLink() throws Exception {
        UUID cartItemID = UUID.fromString("136be68b-6916-4f39-8ef9-19f2267a88f6");
        CartItemModel entity = mockCartItem.mockCartItemEntity(cartItemID);
        entity.setId(cartItemID);
        ProductModel productVO = mockProduct.mockProductEntity(cartItemID);
        when(cartItemRepository.findById(cartItemID)).thenReturn(Optional.of(entity));
        CartItemVO result = cartItemServices.findById(cartItemID);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        System.out.println("Links: " + result.getLinks());
        assertTrue(result.getLinks().hasSize(1));
        assertEquals(productVO, result.getProduct());
        assertEquals(10, result.getQuantity());

    }

    // Testing find all possibilities
    @Test
    void testFindByIdIfTheCartItemIsNotNull() {
        Exception exception = assertThrows(CartItemNotFound.class,
                () -> cartItemServices.findById(null));

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(CART_ITEM_NOT_FOUND_MESSAGE));
    }

    @Test
    void testFindAllIfHaveHateoasLink() throws Exception {
        List<CartItemModel> CartItemList = mockCartItem.mockCartItemEntityList();
        when(cartItemRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(CartItemList));
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<EntityModel<CartItemVO>> allCartItems = cartItemServices.findAll(pageRequest);
        assertEquals(14, CartItemList.size());

        EntityModel<CartItemVO> cartItem1 = allCartItems.getContent().get(1);
        assertNotNull(cartItem1);
        assertNotNull(cartItem1.getLinks());
        assertNotNull(cartItem1.getContent().getId());
        assertTrue(cartItem1.getLinks().hasSize(1));
        assertNotNull(cartItem1.getContent().getQuantity());
        assertNotNull(cartItem1.getContent().getProduct());

        EntityModel<CartItemVO> cartItem7 = allCartItems.getContent().get(7);
        assertNotNull(cartItem7);
        assertNotNull(cartItem7.getLinks());
        assertNotNull(cartItem7.getContent().getId());
        assertTrue(cartItem7.getLinks().hasSize(1));
        assertNotNull(cartItem7.getContent().getQuantity());
        assertNotNull(cartItem7.getContent().getProduct());

        EntityModel<CartItemVO> cartItem10 = allCartItems.getContent().get(10);
        assertNotNull(cartItem10);
        assertNotNull(cartItem10.getLinks());
        assertNotNull(cartItem10.getContent().getId());
        assertTrue(cartItem10.getLinks().hasSize(1));
        assertNotNull(cartItem10.getContent().getQuantity());
        assertNotNull(cartItem10.getContent().getProduct());

    }

    // Testing delete  possibilities
    @Test
    void testDelete() throws Exception {
        UUID cartItemId = UUID.fromString("136be68b-6916-4f39-8ef9-19f2267a88f6");
        CartItemModel entity = mockCartItem.mockCartItemEntity(cartItemId);
        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.ofNullable(entity));
        cartItemServices.delete(cartItemId);


    }

    // Testing cartItem Had Quantity Available possibilities

    @Test
    void testCartItemHadQuantityAvailableWithQuantityEquals0() {
        UUID cartItemId = UUID.fromString("136be68b-6916-4f39-8ef9-19f2267a88f6");
        CartItemVO entity = mockCartItem.mockCartItemVO(cartItemId);
        entity.setQuantity(0);
        Exception exception = assertThrows(InsufficientQuantityAvailable.class,
                () -> cartItemServices.cartItemHadQuantityAvailable(entity));

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(QUANTITY_MESSAGE));

    }

    // Testing check If Quantity Is Less possibilities



    @Test
    void testCheckIfQuantityIsLessWithNullCartItemVO(){


        UUID cartItemId = UUID.fromString("136be68b-6916-4f39-8ef9-19f2267a88f6");
        CartItemModel entity = mockCartItem.mockCartItemEntity(cartItemId);
        ProductModel productModel = mockProduct.mockProductEntity(cartItemId);
        entity.setProduct(productModel);
        Exception exception = assertThrows(RequiredObjectsNullException.class,
                () -> cartItemServices.checkIfQuantityIsLess(null,entity));

        assertEquals(REQUIRED_OBJECT_MESSAGE,
                exception.getMessage());
    }

    @Test
    void testCheckIfQuantityIsLessWithNullCartItemModel(){
        UUID cartItemId = UUID.fromString("136be68b-6916-4f39-8ef9-19f2267a88f6");
        CartItemVO vo = mockCartItem.mockCartItemVO(cartItemId);
        Exception exception = assertThrows(RequiredObjectsNullException.class,
                () -> cartItemServices.checkIfQuantityIsLess(vo,null));

        assertEquals(REQUIRED_OBJECT_MESSAGE,
                exception.getMessage());
    }

    @Test
    void testCheckIfQuantityIsLessWithNullQuantity(){
        UUID cartItemId = UUID.fromString("136be68b-6916-4f39-8ef9-19f2267a88f6");
        CartItemVO vo = mockCartItem.mockCartItemVO(cartItemId);
        vo.setQuantity(null);
        CartItemModel entity = mockCartItem.mockCartItemEntity(cartItemId);
        ProductModel productModel = mockProduct.mockProductEntity(cartItemId);
        entity.setProduct(productModel);
        Exception exception = assertThrows(RequiredObjectsNullException.class,
                () -> cartItemServices.checkIfQuantityIsLess(vo,entity));

        assertEquals(REQUIRED_OBJECT_MESSAGE,
                exception.getMessage());
    }

    @Test
    void testCheckIfQuantityIsLessWithNullProductQuantity(){
        UUID cartItemId = UUID.fromString("136be68b-6916-4f39-8ef9-19f2267a88f6");
        CartItemVO vo = mockCartItem.mockCartItemVO(cartItemId);
        CartItemModel entity = mockCartItem.mockCartItemEntity(cartItemId);
        ProductModel productModel = mockProduct.mockProductEntity(cartItemId);
        productModel.setQuantity(null);
        entity.setProduct(productModel);
        Exception exception = assertThrows(RequiredObjectsNullException.class,
                () -> cartItemServices.checkIfQuantityIsLess(vo,entity));

        assertEquals(REQUIRED_OBJECT_MESSAGE,
                exception.getMessage());
    }

    @Test
    void testCheckIfQuantityIsLessWithNullProduct(){
        UUID cartItemId = UUID.fromString("136be68b-6916-4f39-8ef9-19f2267a88f6");
        CartItemVO vo = mockCartItem.mockCartItemVO(cartItemId);
        CartItemModel entity = mockCartItem.mockCartItemEntity(cartItemId);
        entity.setProduct(null);
        Exception exception = assertThrows(RequiredObjectsNullException.class,
                () -> cartItemServices.checkIfQuantityIsLess(vo,entity));

        assertEquals(REQUIRED_OBJECT_MESSAGE,
                exception.getMessage());
    }

    @Test
    void testCheckIfQuantityIsLessWithSufficientQuantity() {

        UUID cartItemId = UUID.fromString("136be68b-6916-4f39-8ef9-19f2267a88f6");
        CartItemVO vo = mockCartItem.mockCartItemVO(cartItemId);
        vo.setQuantity(5);
        CartItemModel entity = mockCartItem.mockCartItemEntity(cartItemId);
        ProductModel productModel = mockProduct.mockProductEntity(cartItemId);
        entity.setProduct(productModel);
        assertDoesNotThrow(() -> cartItemServices.checkIfQuantityIsLess(vo, entity));

    }

    @Test
    void testCheckIfQuantityIsLessWithInsufficientQuantity(){
        UUID cartItemId = UUID.fromString("136be68b-6916-4f39-8ef9-19f2267a88f6");
        CartItemVO vo = mockCartItem.mockCartItemVO(cartItemId);
        vo.setQuantity(15);
        CartItemModel entity = mockCartItem.mockCartItemEntity(cartItemId);
        ProductModel productModel = mockProduct.mockProductEntity(cartItemId);
        entity.setProduct(productModel);
        Exception exception = assertThrows(InsufficientQuantityAvailable.class,
                () -> cartItemServices.checkIfQuantityIsLess(vo,entity));

        assertEquals(INSUFFICIENT_AVAILABLE_QUANTITY_MESSAGE,
                exception.getMessage());
    }




}
