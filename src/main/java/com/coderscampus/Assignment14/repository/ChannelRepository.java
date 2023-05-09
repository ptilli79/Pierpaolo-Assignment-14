package com.coderscampus.Assignment14.repository;


import java.util.List;



import org.springframework.stereotype.Repository;

import com.coderscampus.Assignment14.domain.Channel;


//ChannelRepository.java
import java.util.HashMap;

import java.util.Map;
import java.util.stream.Collectors;
@Repository
public class ChannelRepository {
 private Map<String, Channel> channels;

 public ChannelRepository() {
     channels = new HashMap<>();
 }

 public void save(Channel channel) {
     channels.put(channel.getId(), channel);
 }

 public Channel findById(String id) {
     return channels.get(id);
 }

 public List<Channel> findAll() {
     return channels.values().stream().collect(Collectors.toList());
 }

 public boolean existsById(String id) {
     return channels.containsKey(id);
 }
}