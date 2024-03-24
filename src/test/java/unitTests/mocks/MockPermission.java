package unitTests.mocks;

import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.user.PermissionVO;
import com.github.GuilhermeBauer.Ecommerce.model.PermissionModel;
import unitTests.mocks.contract.MockClassContract;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockPermission implements MockClassContract<PermissionModel, PermissionVO> {



    @Override
    public PermissionModel mockMEntity() {
        return mockMEntity(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17"));

    }

    @Override
    public PermissionVO mockVVO() {
        return mockVVO(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17"));

    }

    @Override
    public PermissionModel mockMEntity(UUID uuid) {
        PermissionModel permissionModel = new PermissionModel();
        permissionModel.setId(uuid);
        permissionModel.setDescription("USER");
        return permissionModel;
    }

    @Override
    public PermissionVO mockVVO(UUID uuid) {
        PermissionVO permissionVO = new PermissionVO();
        permissionVO.setId(uuid);
        permissionVO.setDescription("USER");
        return permissionVO;
    }

    @Override
    public List<PermissionModel> mockMEntityList() {
        List<PermissionModel> permissionModelList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            permissionModelList.add(mockMEntity(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17")));

        }
        return permissionModelList;
    }

    @Override
    public List<PermissionVO> mockVVOList() {
        List<PermissionVO> permissionVOList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            permissionVOList.add(mockVVO(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17")));

        }
        return permissionVOList;
    }
}

