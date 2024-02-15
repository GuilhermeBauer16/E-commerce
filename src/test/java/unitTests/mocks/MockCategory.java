package unitTests.mocks;

import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.CategoryVO;
import com.github.GuilhermeBauer.Ecommerce.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockCategory {

    public CategoryModel mockCategoryEntity(){return mockCategoryEntity(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17"));}
    public CategoryVO mockCategoryVO(){return mockCategoryVO(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17"));}

    public CategoryModel mockCategoryEntity(UUID uuid){
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setName("SHOES");
        return categoryModel;
    }

    public CategoryVO mockCategoryVO(UUID uuid){
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setName("SHOES");
        return categoryVO;
    }

    public List<CategoryModel> mockCategoryEntityList(){
        List<CategoryModel> categoryModelList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            categoryModelList.add(mockCategoryEntity(UUID.randomUUID()));

        }
        return categoryModelList;
    }

    public List<CategoryVO> mockCategoryVOList(){
        List<CategoryVO> categoryVOList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            categoryVOList.add(mockCategoryVO(UUID.randomUUID()));

        }
        return categoryVOList;
    }
}

