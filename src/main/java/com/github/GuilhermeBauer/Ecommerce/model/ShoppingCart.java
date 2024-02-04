package com.github.GuilhermeBauer.Ecommerce.model;

import java.io.Serializable;
import java.util.*;

public class ShoppingCart  implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private UserModel user;

    private List<CartItem> itens = new ArrayList<>();

    private Date date;

    public ShoppingCart() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public List<CartItem> getItens() {
        return itens;
    }

    public void setItens(List<CartItem> itens) {
        this.itens = itens;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(itens, that.itens) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, itens, date);
    }
}
