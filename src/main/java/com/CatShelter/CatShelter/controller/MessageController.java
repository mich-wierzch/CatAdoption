package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.dto.ReceivedChatMessageDto;
import com.CatShelter.CatShelter.dto.SendChatMessageDto;
import com.CatShelter.CatShelter.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/send/{userId}")
    public ResponseEntity<String> sendMessage(@RequestBody SendChatMessageDto textMessage, @PathVariable Long userId){
        return messageService.sendMessage(textMessage, userId);
    }

    @GetMapping("/received-all/{userId}")
    public List<ReceivedChatMessageDto> getAllReceivedMessages(@PathVariable Long userId){
        return messageService.getAllReceivedMessages(userId);
    }
    @GetMapping("/received-from/{senderId}")
    public List<ReceivedChatMessageDto> getMessagesBySender(@PathVariable Long senderId){
        return messageService.getMessagesBySender(senderId);
    }

}
