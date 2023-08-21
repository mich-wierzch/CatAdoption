//package com.CatShelter.CatShelter.mapper;
//
//import com.CatShelter.CatShelter.dto.ReceivedChatMessageDto;
//import com.CatShelter.CatShelter.model.ChatMessage;
//import com.CatShelter.CatShelter.repository.UserRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@AllArgsConstructor
//public class ReceivedChatMessageMapper {
//
//    private final UserRepository userRepository;
//    public ReceivedChatMessageDto convertToDto(ChatMessage message){
//        return ReceivedChatMessageDto.builder()
//                .sender(userRepository.findByUserId(message.getSenderId()).getUsername())
//                .timestamp(message.getTimestamp())
//                .content(message.getContent())
//                .build();
//    }
//}
