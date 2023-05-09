package com.coderscampus.Assignment14.repository;


import java.util.List;


import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.coderscampus.Assignment14.domain.Message;


//MessageRepository.java

import java.util.HashMap;

import java.util.Map;


@Repository
public class MessageRepository {
 private Map<String, Message> messages;

 public MessageRepository() {
     messages = new HashMap<>();
 }

 public void save(Message message) {
     messages.put(message.getId(), message);
 }

 public List<Message> findByChannelId(String channelId) {
     return messages.values().stream()
         .filter(message -> message.getChannel().getId().equals(channelId))
         .collect(Collectors.toList());
 }
}