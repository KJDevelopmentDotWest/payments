package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.profilepicture.ProfilePicture;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.profilepicturedto.ProfilePictureDto;

public class ProfilePictureConverter implements Converter<ProfilePicture, ProfilePictureDto, Integer> {
    @Override
    public ProfilePicture convert(ProfilePictureDto value) {
        return new ProfilePicture(value.getName(),
                value.getPath());
    }

    @Override
    public ProfilePictureDto convert(ProfilePicture value) {
        return new ProfilePictureDto(value.getId(),
                value.getName(),
                value.getPath());
    }
}
