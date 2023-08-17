//package com.CatShelter.CatShelter.controller;
//
//import com.CatShelter.CatShelter.dto.ReceivedChatMessageDto;
//import com.CatShelter.CatShelter.dto.SendChatMessageDto;
//import com.CatShelter.CatShelter.model.ChatMessage;
//import com.CatShelter.CatShelter.service.MessageService;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/message")
//@AllArgsConstructor
//public class MessageController {
//
//    private final MessageService messageService;
//
//    @PostMapping("/send/{userId}")
//    public String sendMessage(@RequestBody SendChatMessageDto textMessage, @PathVariable Long userId){
//        return messageService.sendMessage(textMessage, userId);
//    }
//
//    @GetMapping("/findAllReceived/{userId}")
//    public List<ReceivedChatMessageDto> getReceivedMessages(@PathVariable Long userId){
//        return messageService.getReceivedMessages(userId);
//    }
//
//}
