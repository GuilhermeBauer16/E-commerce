package unitTests.mocks;

import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.ProductVO;
import com.github.GuilhermeBauer.Ecommerce.model.CategoryModel;
import com.github.GuilhermeBauer.Ecommerce.model.ProductModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockProduct {


    public ProductModel mockProductEntity() {
        return mockProductEntity(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17"));
    }


    public ProductVO mockProductVo() {
        return mockProductVo(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17"));
    }


    public ProductModel mockProductEntity(UUID uuid) {
        MockCategory mockCategory = new MockCategory();
        CategoryModel mockCategoryEntity = mockCategory.mockCategoryEntity(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17"));
        ProductModel productModel = new ProductModel();
        productModel.setId(uuid);
        productModel.setName("Air max");
        productModel.setDescription("the shoes");
        productModel.setCategoryModel(mockCategoryEntity);
        productModel.setBranch("nike");
        productModel.setPrice(100.00);
        productModel.setQuantity(10);
        productModel.setAvailable(productModel.getQuantity() > 1
                ? true : false);
        return productModel;
    }

    public List<ProductModel> mockProductEntityList() {
        List<ProductModel> products = new ArrayList<>();
        for (int i = 0; i < 14; i++) {

            products.add(mockProductEntity(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17")));

        }
        return products;
    }

    public List<ProductVO> mockProductVOList() {
        List<ProductVO> products = new ArrayList<>();
        for (int i = 0; i < 14; i++) {

            products.add(mockProductVo(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17")));

        }
        return products;
    }

    public ProductVO mockProductVo(UUID uuid) {
        MockCategory mockCategory = new MockCategory();
        CategoryModel mockCategoryEntity = mockCategory.mockCategoryEntity(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17"));
        ProductVO productVO = new ProductVO();
        productVO.setId(uuid);
        productVO.setName("Air max");
        productVO.setDescription("the shoes");
        productVO.setBranch("nike");
        productVO.setCategoryModel(mockCategoryEntity);
        productVO.setPrice(100.00);
        productVO.setQuantity(10);
        productVO.setAvailable(productVO.getQuantity() > 1
                ? true : false);
        return productVO;
    }


}



