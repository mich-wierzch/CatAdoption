//package com.CatShelter.CatShelter.service;
//
//import com.CatShelter.CatShelter.config.MQConfig;
//import com.CatShelter.CatShelter.dto.ReceivedChatMessageDto;
//import com.CatShelter.CatShelter.dto.SendChatMessageDto;
//import com.CatShelter.CatShelter.mapper.ReceivedChatMessageMapper;
//import com.CatShelter.CatShelter.model.ChatMessage;
//import com.CatShelter.CatShelter.repository.ChatMessageRepository;
//import com.CatShelter.CatShelter.repository.UserRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@AllArgsConstructor
//public class MessageService {
//
//    private RabbitTemplate template;
//    private final ChatMessageRepository chatMessageRepository;
//    private final UserRepository userRepository;
//    private final AuthenticationService authenticationService;
//    private final ReceivedChatMessageMapper receivedChatMessageMapper;
//
//
//    public String sendMessage(SendChatMessageDto sendChatMessageDto, Long userId){
//        ChatMessage message = ChatMessage.builder()
//                .senderId(authenticationService.getCurrentUserId())
//                .recipientId(userId)
//                .content(sendChatMessageDto.getContent())
//                .timestamp(new Date())
//                .build();
//
//        template.convertAndSend(MQConfig.EXCHANGE,
//                MQConfig.ROUTING_KEY, message);
//        return "Message sent";
//    }
//
//    @RabbitListener(queues = MQConfig.QUEUE)
//    public void listener(ChatMessage message){
//        chatMessageRepository.save(message);
//    }
//
//    public List<ReceivedChatMessageDto> getReceivedMessages(Long userId){
//        List<ChatMessage> messages = chatMessageRepository.findAllByRecipientIdOrderByTimestampAsc(userId);
//        return messages.stream()
//                .map(receivedChatMessageMapper::convertToDto)
//                .collect(Collectors.toList());
//
//    }
//}
