package com.foolself.demo.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Permission {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String url;

    @ManyToMany
    @JoinTable(name = "RolePermission", joinColumns = @JoinColumn(name = "permissionId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    private List<Role> roleList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
