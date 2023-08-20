package com.effectivemobile.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.effectivemobile.entity.Image;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String header;
    private String text;
    private List<Image> images;
}
