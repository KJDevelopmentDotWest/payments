package com.epam.jwd.dao.model.profilepicture;

import com.epam.jwd.dao.model.Entity;

import java.util.Objects;

public class ProfilePicture extends Entity<Integer> {

    private String name;
    private String path;

    public ProfilePicture(String name, String path) {
        this.name = name;
        this.path = path;
    }


    public ProfilePicture(Integer id, String name, String path) {
        this.name = name;
        this.path = path;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfilePicture profilePicture = (ProfilePicture) o;
        return Objects.equals(name, profilePicture.getName())
                && Objects.equals(path, profilePicture.getPath())
                && Objects.equals(id, profilePicture.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, path, id);
    }

    @Override
    public String toString() {
        return "ProfilePicture{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
