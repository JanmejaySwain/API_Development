package com.myblogrestapi.payload;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;
    @NotEmpty
    @Size(min = 2,message = "title should have at least 2 characters")
    private String title;
    @NotEmpty
    @Size(min=10,message = "description should have at least minimum 10 characters")
    private String description;
    @NotBlank(message = "content should not be empty")
    private String content;
}
