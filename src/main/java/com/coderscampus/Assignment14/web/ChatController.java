package com.coderscampus.Assignment14.web;

import com.coderscampus.Assignment14.domain.Channel;
import com.coderscampus.Assignment14.domain.Message;
import com.coderscampus.Assignment14.domain.User;
import com.coderscampus.Assignment14.service.ChannelService;
import com.coderscampus.Assignment14.service.MessageService;
import com.coderscampus.Assignment14.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/user")
    @ResponseBody
    public ResponseEntity<Object> createUser(@RequestParam("name") String name) {
        if (userService.findUserByName(name) != null) {
            return new ResponseEntity<>("User with the provided name already exists.", HttpStatus.CONFLICT);
        }

        User user = userService.createUser(name);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    @ResponseBody
    public User getUserById(@PathVariable String userId) {
        return userService.findUserById(userId);
    }

    @GetMapping("/channels")
    @ResponseBody
    public List<Channel> getChannels() {
        return channelService.findAllChannels();
    }

    @GetMapping("/users")
    @ResponseBody
    public List<User> getUsers() {
        return userService.findAllUsers();
    }

    @PostMapping("/channel")
    @ResponseBody
    public Channel createChannel(@RequestParam("name") String name) {
        return channelService.createChannel(name);
    }

    @GetMapping("/channels/{channelId}")
    public String joinChannel(@PathVariable("channelId") String channelId, @RequestParam("userId") String userId, Model model) {
        Channel channel = channelService.findChannelById(channelId);
        User user = userService.findUserById(userId);
        model.addAttribute("channel", channel);
        model.addAttribute("user", user);
        return "channel";
    }

    @GetMapping("/channel/{channelId}/messages")
    @ResponseBody
    public List<Message> getMessages(@PathVariable("channelId") String channelId) {
        return messageService.findMessagesByChannelId(channelId);
    }

    @PostMapping("/channel/{channelId}/message")
    @ResponseBody
    public Message createMessage(
            @PathVariable("channelId") String channelId,
            @RequestParam("userId") String userId,
            @RequestParam("text") String text
    ) {
        Channel channel = channelService.findChannelById(channelId);
        User user = userService.findUserById(userId);
        return messageService.createMessage(channelId, userId, text, channel, user);
    }
}
