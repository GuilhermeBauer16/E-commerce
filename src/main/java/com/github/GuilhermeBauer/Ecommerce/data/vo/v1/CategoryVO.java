package com.github.GuilhermeBauer.Ecommerce.data.vo.v1;

import java.util.Objects;
import java.util.UUID;

public class CategoryVO {

    private UUID key;

    private String name;

    public CategoryVO() {
    }

    public UUID getKey() {
        return key;
    }

    public void setKey(UUID key) {
        this.key = key;
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
        return Objects.equals(key, that.key) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, name);
    }
}
