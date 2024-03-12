package com.github.GuilhermeBauer.Ecommerce.mapper;

import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.*;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.user.PermissionVO;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.user.UserVO;
import com.github.GuilhermeBauer.Ecommerce.model.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class Mapper {



    private static final ModelMapper mapper = new ModelMapper();

    static {
        // to entity for VO
        mapper.createTypeMap(ProductModel.class, ProductVO.class)
                .addMapping(ProductModel::getId, ProductVO::setId);

        mapper.createTypeMap(CategoryModel.class, CategoryVO.class)
                .addMapping(CategoryModel::getId,CategoryVO::setId);

        mapper.createTypeMap(CartItem.class, CartItemVO.class)
                .addMapping(CartItem::getId , CartItemVO::setId);

        mapper.createTypeMap(PermissionModel.class, PermissionVO.class)
                .addMapping(PermissionModel::getId , PermissionVO::setId);

        mapper.createTypeMap(UserModel.class, UserVO.class)
                .addMapping(UserModel::getId , UserVO::setId);

        // to VO for entity

        mapper.createTypeMap(ProductVO.class , ProductModel.class)
                .addMapping(ProductVO::getId, ProductModel::setId);

        mapper.createTypeMap(CategoryVO.class, CategoryModel.class)
                .addMapping(CategoryVO::getId, CategoryModel::setId);

        mapper.createTypeMap(CartItemVO.class , CartItem.class)
                .addMapping(CartItemVO::getId, CartItem::setId);

        mapper.createTypeMap(PermissionVO.class , PermissionModel.class)
                .addMapping(PermissionVO::getId, PermissionModel::setId);

        mapper.createTypeMap(UserVO.class , UserModel.class)
                .addMapping(UserVO::getId, UserModel::setId);





    }

    public static <O,D> D parseObject(O origin, Class<D> destination){
        return mapper.map(origin,destination);
    }

    public static <O,D> List<D> parseObjectList(List<O> origin, Class<D> destination){
        List<D> destinationObjects = new ArrayList<>();

        for (O o : origin){
            destinationObjects.add(mapper.map(o,destination));
        }

        return destinationObjects;
    }
}
