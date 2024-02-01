package com.github.GuilhermeBauer.Ecommerce.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class ProductModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID id;
    private String name;
    private String description;
    private String Branch;

    private CategoryModel categoryModel;

//    private String image;


    public ProductModel() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductModel product = (ProductModel) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(Branch, product.Branch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, Branch);
    }
}
