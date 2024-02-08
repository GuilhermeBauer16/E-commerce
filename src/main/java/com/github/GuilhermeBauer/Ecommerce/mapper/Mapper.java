package com.github.GuilhermeBauer.Ecommerce.mapper;

import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.CartItemVO;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.CategoryVO;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.ProductVO;
import com.github.GuilhermeBauer.Ecommerce.model.CartItem;
import com.github.GuilhermeBauer.Ecommerce.model.CategoryModel;
import com.github.GuilhermeBauer.Ecommerce.model.ProductModel;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class Mapper {



    private static final ModelMapper mapper = new ModelMapper();

    static {
        // to entity for VO
        mapper.createTypeMap(ProductModel.class, ProductVO.class)
                .addMapping(ProductModel::getId, ProductVO::setKey);

        mapper.createTypeMap(CategoryModel.class, CategoryVO.class)
                .addMapping(CategoryModel::getId,CategoryVO::setKey);

        mapper.createTypeMap(CartItem.class, CartItemVO.class)
                .addMapping(CartItem::getId , CartItemVO::setKey);

        // to VO for entity

        mapper.createTypeMap(ProductVO.class , ProductModel.class)
                .addMapping(ProductVO::getKey, ProductModel::setId);

        mapper.createTypeMap(CategoryVO.class, CategoryModel.class)
                .addMapping(CategoryVO::getKey , CategoryModel::setId);

        mapper.createTypeMap(CartItemVO.class , CartItem.class)
                .addMapping(CartItemVO::getKey, CartItem::setId);





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
