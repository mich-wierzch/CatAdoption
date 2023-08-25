package com.CatShelter.CatShelter.repository;

import com.CatShelter.CatShelter.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByRecipientIdOrderBySenderId(Long recipientId);
    List<ChatMessage> findAllBySenderId(Long senderId);

    void deleteByTimestampBefore(Date timestamp);


}
