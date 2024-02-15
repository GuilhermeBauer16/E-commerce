package com.github.GuilhermeBauer.Ecommerce.data.vo.v1;

import com.github.GuilhermeBauer.Ecommerce.model.CategoryModel;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class ProductVO extends RepresentationModel<ProductVO> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private UUID id;
    private String name;
    private String description;
    private String Branch;
    private Double price;
    private CategoryModel categoryModel;
    private Boolean isAvailable;
    private Integer quantity;

    public ProductVO() {
    }
    //    private String image;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductVO productVO = (ProductVO) o;
        return quantity == productVO.quantity && Objects.equals(id, productVO.id) && Objects.equals(name, productVO.name) && Objects.equals(description, productVO.description) && Objects.equals(Branch, productVO.Branch) && Objects.equals(price, productVO.price) && Objects.equals(categoryModel, productVO.categoryModel) && Objects.equals(isAvailable, productVO.isAvailable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, Branch, price, categoryModel, isAvailable, quantity);
    }
}

