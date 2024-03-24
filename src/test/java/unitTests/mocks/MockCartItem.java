package unitTests.mocks;

import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.CartItemVO;
import com.github.GuilhermeBauer.Ecommerce.model.CartItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockCartItem {

    private MockProduct mockProduct = new MockProduct();

    public CartItemModel mockCartItemEntity(){return mockCartItemEntity(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17"));}
    public CartItemVO mockCartItemVO(){return mockCartItemVO(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17"));}

    public CartItemModel mockCartItemEntity(UUID uuid){
        CartItemModel cartItemModel = new CartItemModel();
        cartItemModel.setId(uuid);
        cartItemModel.setProduct(mockProduct.mockProductEntity(uuid));
        cartItemModel.setQuantity(10);
        return cartItemModel;
    }

    public CartItemVO mockCartItemVO(UUID uuid){
        CartItemVO cartItemVO = new CartItemVO();
        cartItemVO.setId(uuid);
        cartItemVO.setProduct(mockProduct.mockProductEntity(uuid));
        cartItemVO.setQuantity(10);
        return cartItemVO;
    }

    public List<CartItemModel> mockCartItemEntityList(){
        List<CartItemModel> categoryModelList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            categoryModelList.add(mockCartItemEntity(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17")));

        }
        return categoryModelList;
    }

    public List<CartItemVO> mockCategoryVOList(){
        List<CartItemVO> categoryVOList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            categoryVOList.add(mockCartItemVO(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17")));

        }
        return categoryVOList;
    }
}

