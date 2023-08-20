package com.effectivemobile.mapper;

import com.effectivemobile.entity.Post;
import com.effectivemobile.model.response.PostResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostResponseDto toResponseDto(Post post);

    List<PostResponseDto> toResponseDtos(List<Post> posts);
}
