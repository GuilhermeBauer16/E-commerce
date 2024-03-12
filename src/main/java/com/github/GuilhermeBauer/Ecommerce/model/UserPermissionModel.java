//package com.github.GuilhermeBauer.Ecommerce.model;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name="user_permission")
//public class UserPermissionModel {
//
//    @Id
//    @ManyToOne
//    @JoinColumn(name = "id_user")
//    private UserModel userModel;
//
//    @Id
//    @ManyToOne
//    @JoinColumn(name = "id_permission")
//    private PermissionModel permissionModel;
//
//    public UserPermissionModel(UserModel userModel, PermissionModel permissionModel) {
//        this.userModel = userModel;
//        this.permissionModel = permissionModel;
//    }
//
//
//
//    public UserModel getUserModel() {
//        return userModel;
//    }
//
//
//    public void setUserModel(UserModel userModel) {
//        this.userModel = userModel;
//    }
//
//    public PermissionModel getPermissionModel() {
//        return permissionModel;
//    }
//
//    public void setPermissionModel(PermissionModel permissionModel) {
//        this.permissionModel = permissionModel;
//    }
//}
