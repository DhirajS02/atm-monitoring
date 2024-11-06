package com.atm.atm_monitoring.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.HashSet;

public class Employee implements UserDetails {
    private String employeeCode; // This will act as the user ID
    private String employeeName;
    private String password; // Password field
    private Boolean isActivated;
    private Set<Role> roles = new HashSet<>();

    public Employee() {
    }

    public Employee(String employeeCode, String employeeName, String password, Boolean isActivated, Set<Role> roles) {
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
        this.password = password;
        this.isActivated = isActivated;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities=new ArrayList<GrantedAuthority>();
        for(Role role:roles)
        {
            GrantedAuthority grantedAuthority=new SimpleGrantedAuthority(role.getName().toString());
            grantedAuthorities.add(grantedAuthority);
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return employeeName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return isActivated;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActivated(Boolean activated) {
        isActivated = activated;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Boolean getActivated() {
        return isActivated;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
