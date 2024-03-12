package com.github.GuilhermeBauer.Ecommerce.data.vo.v1.user;

import com.github.GuilhermeBauer.Ecommerce.model.PermissionModel;
import com.github.GuilhermeBauer.Ecommerce.model.UserModel;

import java.util.Objects;

public class UserPermissionVO {

    private UserModel userModel;


    private PermissionModel permissionModel;

    public UserPermissionVO(UserModel userModel, PermissionModel permissionModel) {
        this.userModel = userModel;
        this.permissionModel = permissionModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public PermissionModel getPermissionModel() {
        return permissionModel;
    }

    public void setPermissionModel(PermissionModel permissionModel) {
        this.permissionModel = permissionModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPermissionVO that = (UserPermissionVO) o;
        return Objects.equals(userModel, that.userModel) && Objects.equals(permissionModel, that.permissionModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userModel, permissionModel);
    }
}
