package com.github.GuilhermeBauer.Ecommerce.data.vo.v1.user;

import com.github.GuilhermeBauer.Ecommerce.model.PermissionModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class UserVO implements UserDetails, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private UUID id;

    private String username;

    private String fullName;
    private String password;


    private Boolean accountNonExpired;


    private Boolean accountNonLocked;


    private Boolean credentialsNonExpired;

    private Boolean enabled;

    //    private List<UserPermissionModel> permissions;
    private PermissionModel permission;

    public UserVO() {
    }

    public List<String> getRoles() {
        List<String> roles = new ArrayList<>();
        roles.add(permission.getDescription());
        return roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(permission.getDescription()));
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public PermissionModel getPermission() {
        return permission;
    }

    public void setPermission(PermissionModel permission) {
        this.permission = permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserVO userVO = (UserVO) o;
        return Objects.equals(id, userVO.id) && Objects.equals(username, userVO.username) && Objects.equals(fullName, userVO.fullName) && Objects.equals(password, userVO.password) && Objects.equals(accountNonExpired, userVO.accountNonExpired) && Objects.equals(accountNonLocked, userVO.accountNonLocked) && Objects.equals(credentialsNonExpired, userVO.credentialsNonExpired) && Objects.equals(enabled, userVO.enabled) && Objects.equals(permission, userVO.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, fullName, password, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled, permission);
    }
}
