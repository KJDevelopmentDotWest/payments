package com.epam.jwd.repository.model.user;

import com.epam.jwd.repository.model.Entity;

public class Role extends Entity<Integer> {
    private String name;
    private Boolean permissionCanUnlock;
    private Boolean permissionCanViewAll;
    private Boolean permissionCanCreateRole;

    public Role(Integer id) {
        super(id);
    }

    public Role(Integer id, String name, Boolean permissionCanUnlock, Boolean permissionCanViewAll, Boolean permissionCanCreateRole) {
        super(id);
        this.name = name;
        this.permissionCanUnlock = permissionCanUnlock;
        this.permissionCanViewAll = permissionCanViewAll;
        this.permissionCanCreateRole = permissionCanCreateRole;
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
}
