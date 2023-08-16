package com.CatShelter.CatShelter.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserCommentDto {
    private String commenter;
    private String user;
    private String text;
    private LocalDateTime timestamp;
}
