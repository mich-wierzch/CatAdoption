package com.CatShelter.CatShelter.service;

import com.CatShelter.CatShelter.config.MQConfig;
import com.CatShelter.CatShelter.dto.ReceivedChatMessageDto;
import com.CatShelter.CatShelter.dto.SendChatMessageDto;
import com.CatShelter.CatShelter.mapper.ReceivedChatMessageMapper;
import com.CatShelter.CatShelter.model.ChatMessage;
import com.CatShelter.CatShelter.repository.ChatMessageRepository;
import com.CatShelter.CatShelter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageService {

    private RabbitTemplate template;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final ReceivedChatMessageMapper receivedChatMessageMapper;


    public ResponseEntity<String> sendMessage(SendChatMessageDto sendChatMessageDto, Long userId){
    try {
        if (userId.equals(authenticationService.getCurrentUserId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not allowed to send messages to oneself");
        }
        ChatMessage message = ChatMessage.builder()
                .senderId(authenticationService.getCurrentUserId())
                .recipientId(userId)
                .content(sendChatMessageDto.getContent())
                .timestamp(new Date())
                .build();


        template.convertAndSend(MQConfig.EXCHANGE,
                MQConfig.ROUTING_KEY, message);
        return ResponseEntity.ok("Message sent");
    } catch (NullPointerException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user logged in!");
    }
    }

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listener(ChatMessage message) {
        chatMessageRepository.save(message);
    }

    public List<ReceivedChatMessageDto> getAllReceivedMessages(Long userId){
        try {
            if(!userId.equals(authenticationService.getCurrentUserId())) return null;
            List<ChatMessage> messages = chatMessageRepository.findAllByRecipientIdOrderBySenderId(userId);
            return messages.stream()
                    .map(receivedChatMessageMapper::convertToDto)
                    .collect(Collectors.toList());
        } catch (NullPointerException e){
            return null;
        }

    }

    public List<ReceivedChatMessageDto> getMessagesBySender(Long senderId){
        try {
            List<ChatMessage> messages = chatMessageRepository.findAllBySenderIdAndRecipientId(senderId, authenticationService.getCurrentUserId());
            return messages.stream()
                    .map(receivedChatMessageMapper::convertToDto)
                    .collect(Collectors.toList());
        } catch (NullPointerException e){
            return null;
        }
    }
    @Scheduled(cron = "0 0 2 * * *")
    public void cleanupMessagesOlderThanThirtyDays(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -30);
        Date thirtyDaysAgo = cal.getTime();

        chatMessageRepository.deleteByTimestampBefore(thirtyDaysAgo);
    }
}
