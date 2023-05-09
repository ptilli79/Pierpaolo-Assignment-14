package com.coderscampus.Assignment14.service;

import com.coderscampus.Assignment14.domain.Channel;
import com.coderscampus.Assignment14.domain.Message;
import com.coderscampus.Assignment14.domain.User;
import com.coderscampus.Assignment14.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(String channelId, String userId, String text, Channel channel, User user) {
        String messageId = UUID.randomUUID().toString();
        LocalDateTime timestamp = LocalDateTime.now();
        Message message = new Message(messageId, user, channel, text, timestamp);
        messageRepository.save(message);
        return message;
    }

    public List<Message> findMessagesByChannelId(String channelId) {
        return messageRepository.findByChannelId(channelId);
    }
}