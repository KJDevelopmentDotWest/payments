package com.epam.jwd.repository.model.user;

public class User {

    private int id;
    private String name;
    private Role role;

    private User(){}

    private void setId(int id){
        this.id = id;
    }

    private void setName(String name){
        this.name = name;
    }

    private void setRole(Role role){
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    static class Builder{
        private int id;
        private String name;
        private Role role;

        public User build(){
            User user = new User();
            user.setId(id);
            user.setName(name);
            user.setRole(role);
            return user;
        }
    }
}
