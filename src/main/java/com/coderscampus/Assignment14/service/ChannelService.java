package com.coderscampus.Assignment14.service;

import com.coderscampus.Assignment14.domain.Channel;
import com.coderscampus.Assignment14.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    public Channel createChannel(String name) {
        String channelId = UUID.randomUUID().toString();
        Channel channel = new Channel(channelId, name);
        channelRepository.save(channel);
        return channel;
    }

    public Channel findChannelById(String channelId) {
        return channelRepository.findById(channelId);
    }

    public List<Channel> findAllChannels() {
        return channelRepository.findAll();
    }
}
