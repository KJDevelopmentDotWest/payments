package com.epam.jwd.dao.model.profilepicture;

import com.epam.jwd.dao.model.Entity;

import java.util.Objects;


/**
 * This class represents profile picture
 */
public class ProfilePicture extends Entity<Integer> {

    private final String name;
    private final String path;

    /**
     *
     * @param name name of profile picture
     * @param path path to picture location
     */
    public ProfilePicture(String name, String path) {
        this.name = name;
        this.path = path;
    }

    /**
     *
     * @param id id of profile picture
     * @param name name of profile picture
     * @param path picture location
     */
    public ProfilePicture(Integer id, String name, String path) {
        this.name = name;
        this.path = path;
        this.id = id;
    }

    /**
     *
     * @return name of picture
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return picture location
     */
    public String getPath() {
        return path;
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
