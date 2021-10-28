package com.epam.jwd.service.dto.profilepicturedto;

import com.epam.jwd.service.dto.AbstractDto;

import java.util.Objects;

public class ProfilePictureDto extends AbstractDto<Integer> {

    private final String name;
    private final String path;

    public ProfilePictureDto(String name, String path) {
        this.name = name;
        this.path = path;
    }


    public ProfilePictureDto(Integer id, String name, String path) {
        this.name = name;
        this.path = path;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfilePictureDto profilePictureDto = (ProfilePictureDto) o;
        return Objects.equals(name, profilePictureDto.getName())
                && Objects.equals(path, profilePictureDto.getPath())
                && Objects.equals(id, profilePictureDto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, path, id);
    }

    @Override
    public String toString() {
        return "ProfilePictureDto{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
