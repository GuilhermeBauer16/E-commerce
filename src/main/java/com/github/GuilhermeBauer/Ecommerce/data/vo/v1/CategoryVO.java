package com.github.GuilhermeBauer.Ecommerce.data.vo.v1;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class CategoryVO extends RepresentationModel<CategoryVO> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private UUID id;

    private String name;



    public CategoryVO() {
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryVO that = (CategoryVO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }






}
