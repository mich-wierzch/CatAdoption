package com.CatShelter.CatShelter.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ReceivedChatMessageDto {
    private String sender;
    private String content;
    private Date timestamp;
}
