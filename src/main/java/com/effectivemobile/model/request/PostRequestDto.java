package com.effectivemobile.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PostRequestDto {
    @NotBlank
    @Size(min = 1, max = 50)
    private String header;
    @NotBlank
    @Size(min = 1, max = 255)
    private String text;
    private List<MultipartFile> images;
}
