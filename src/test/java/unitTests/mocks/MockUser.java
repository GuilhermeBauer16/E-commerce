package unitTests.mocks;

import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.user.UserVO;
import com.github.GuilhermeBauer.Ecommerce.model.UserModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import unitTests.mocks.contract.MockClassContract;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockUser implements MockClassContract<UserModel, UserVO> {
     private MockPermission mockPermission = new MockPermission();

    @Override
    public UserModel mockMEntity() {
        return mockMEntity(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17"));
    }

    @Override
    public UserVO mockVVO() {
        return mockVVO(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17"));
    }

    @Override
    public UserModel mockMEntity(UUID uuid) {
        UserModel userModel = new UserModel();
        userModel.setId(uuid);
        userModel.setUsername("John");
        userModel.setPassword("123");
        userModel.setFullName("John Wick");
        userModel.setAccountNonExpired(true);
        userModel.setAccountNonLocked(true);
        userModel.setCredentialsNonExpired(true);
        userModel.setEnabled(true);
        userModel.setPermission(mockPermission.mockMEntity(uuid));
        List<String> roles = new ArrayList<>();
        roles.add(userModel.getPermission().getDescription());
        return userModel;

    }

    @Override
    public UserVO mockVVO(UUID uuid) {
        UserVO userVO = new UserVO();
        userVO.setId(uuid);
        userVO.setUsername("John");
        userVO.setPassword("123");
        userVO.setFullName("John Wick");
        userVO.setAccountNonExpired(true);
        userVO.setAccountNonLocked(true);
        userVO.setCredentialsNonExpired(true);
        userVO.setEnabled(true);
        userVO.setPermission(mockPermission.mockMEntity(uuid));
        List<String> roles = new ArrayList<>();
        roles.add(userVO.getPermission().getDescription());
        return userVO;
    }

    @Override
    public List<UserModel> mockMEntityList() {
        List<UserModel> userModelList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            userModelList.add(mockMEntity(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17")));

        }
        return userModelList;
    }

    @Override
    public List<UserVO> mockVVOList() {
        List<UserVO> userVOList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            userVOList.add(mockVVO(UUID.fromString("270c51f2-0acf-4ca6-bfc3-1c654f0ddd17")));

        }
        return userVOList;
    }

}

