package com.github.GuilhermeBauer.Ecommerce.data.vo.v1;

import com.github.GuilhermeBauer.Ecommerce.model.PermissionModel;
import jakarta.persistence.*;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_permission", joinColumns = {@JoinColumn(name = "id_user")}
            , inverseJoinColumns = {@JoinColumn(name = "id_permission")})
    private List<PermissionModel> permissions;

    public UserVO() {
    }

    public List<String> getRoles(){
        List<String> roles = new ArrayList<>();
        for(PermissionModel personModel: permissions){
            roles.add(personModel.getDescription());

        }
        return roles;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions;
    }

    @Override
    public String getPassword() {
        return this.password ;
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

    public List<PermissionModel> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionModel> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserVO userVO = (UserVO) o;
        return Objects.equals(id, userVO.id) && Objects.equals(username, userVO.username) && Objects.equals(fullName, userVO.fullName) && Objects.equals(password, userVO.password) && Objects.equals(accountNonExpired, userVO.accountNonExpired) && Objects.equals(accountNonLocked, userVO.accountNonLocked) && Objects.equals(credentialsNonExpired, userVO.credentialsNonExpired) && Objects.equals(enabled, userVO.enabled) && Objects.equals(permissions, userVO.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, fullName, password, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled, permissions);
    }
}
