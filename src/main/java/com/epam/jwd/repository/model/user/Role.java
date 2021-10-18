package com.epam.jwd.repository.model.user;

import com.epam.jwd.repository.model.Entity;

public class Role extends Entity<Integer> {
    private String name;
    private Boolean permissionCanUnlock;
    private Boolean permissionCanViewAll;
    private Boolean permissionCanCreateRole;

    public Role() {}

    public Role(String name, Boolean permissionCanUnlock, Boolean permissionCanViewAll, Boolean permissionCanCreateRole) {
        this.name = name;
        this.permissionCanUnlock = permissionCanUnlock;
        this.permissionCanViewAll = permissionCanViewAll;
        this.permissionCanCreateRole = permissionCanCreateRole;
    }

    public Role(Integer id, Boolean permissionCanUnlock, Boolean permissionCanViewAll, Boolean permissionCanCreateRole, String name) {
        this.name = name;
        this.permissionCanUnlock = permissionCanUnlock;
        this.permissionCanViewAll = permissionCanViewAll;
        this.permissionCanCreateRole = permissionCanCreateRole;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPermissionCanUnlock() {
        return permissionCanUnlock;
    }

    public void setPermissionCanUnlock(Boolean permissionCanUnlock) {
        this.permissionCanUnlock = permissionCanUnlock;
    }

    public Boolean getPermissionCanViewAll() {
        return permissionCanViewAll;
    }

    public void setPermissionCanViewAll(Boolean permissionCanViewAll) {
        this.permissionCanViewAll = permissionCanViewAll;
    }

    public Boolean getPermissionCanCreateRole() {
        return permissionCanCreateRole;
    }

    public void setPermissionCanCreateRole(Boolean permissionCanCreateRole) {
        this.permissionCanCreateRole = permissionCanCreateRole;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", permissionCanUnlock=" + permissionCanUnlock +
                ", permissionCanViewAll=" + permissionCanViewAll +
                ", permissionCanCreateRole=" + permissionCanCreateRole +
                '}';
    }
}
