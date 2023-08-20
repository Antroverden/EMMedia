package com.effectivemobile.mapper;

import org.mapstruct.Mapper;
import com.effectivemobile.entity.User;
import com.effectivemobile.model.response.ProfileResponse;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileResponse toProfileResponse(User user);
}
